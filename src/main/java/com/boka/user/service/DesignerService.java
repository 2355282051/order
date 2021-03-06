package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.util.Assert;
import com.boka.common.util.DistanceUtil;
import com.boka.user.dto.DesignerTO;
import com.boka.user.model.*;
import com.boka.user.repository.DesignerRepository;
import com.boka.user.repository.DesignerStarRepository;
import com.boka.user.repository.HonourRepository;
import com.boka.user.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("designerService")
public class DesignerService {

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private DesignerStarRepository designerStarRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private HonourRepository honourRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private S3UserService s3UserService;

    @Autowired
    private DesktopService desktopService;

    @Resource
    private CommentService commentService;


    public List<Designer> findNearDesigners(Location loc, String city, String keyword, int page) {
        //无经纬度则按照同城查找
        if (loc.getLat() == null || loc.getLng() == null) {
            return findCityDesigners(loc, city, keyword, page);
        }

        return designerRepository.findNearDesigners(loc, city, keyword, page);
    }

    public List<Designer> findCityDesigners(Location loc, String city, String keyword, int page) {
        List<Designer> result = designerRepository.findCityDesigners(loc, city, keyword, page);
        //计算距离
        if (loc.getLat() != null && loc.getLng() != null) {
            for (Designer item : result) {
                if (item.getLoc() != null) {
                    item.setDistance(DistanceUtil.distance(loc.getLat(), loc.getLng(), item.getLoc().getLat(), item.getLoc().getLng()));
                }
            }
        }
        return result;
    }

    public DesignerTO getUserInfo(String designerId, String userId, String accessToken, String deviceId) throws CommonException {
        Designer bean = designerRepository.findOne(designerId);
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        Shop shop = bean.getShop();
        if(shop != null && Assert.isNotNull(shop.getId())) {
            shop = shopService.getShop(shop.getId());
            bean.setShop(shop);
        }

        DesignerTO result = new DesignerTO(bean);
        if (accessToken != null) {
            int designerCommentCnt = commentService.getReserveCommentCnt(designerId, accessToken, deviceId);
            result.setCommentCount(designerCommentCnt);
        }

        if (Assert.isNotNull(userId)) {
            Like like = likeRepository.findByDesignerIdAndUserId(designerId, userId);
            if (like != null) {
                result.setLiked(1);
            }
        }
        result.setMobile(bean.getMobile());

        long honourCount = honourRepository.countHonourByDesigner(designerId);

        if (honourCount > 0) {
            result.setHonourStatus(1);
        }
        return result;
    }

    public List<DesignerStar> getDesignerStar(String city) {
        Sort sort = new Sort(Sort.Direction.ASC, "index");
        List<DesignerStar> result = designerStarRepository.findByCity(city, sort);
        if (result != null && result.size() == 4) {
            return result;
        } else if (result == null) {
            result = designerStarRepository.findByCity("310000", sort);
        } else if (result.size() < 4) {
            List<DesignerStar> other = designerStarRepository.findByCity("310000", sort);
            for (int i = 0; i < 4 - result.size(); i++) {
                result.add(other.get(i));
            }
        }
        return result;
    }

    public List<Designer> getShopDesigner(String id) throws IOException {
        List<Designer> result;
        Shop shop = shopService.getShop(id);
        if (shop == null) {
            return null;
        }
        if (shop.getS3Status() == 1) {
            //取S3员工
            result = s3UserService.getDesigner(shop.getChainUrl(), shop.getCustId(), shop.getCompId());

            if (result == null)
                return null;

            //获取发界ID
            List<Designer> designers = designerRepository.findByS3Shop(shop.getCustId(), shop.getCompId());
            for (Designer item : designers) {
                for (Designer designer : result) {
                    if (item.getEmpId().equals(designer.getEmpId())) {
                        designer.setId(item.getId());
                        designer.setAvatar(item.getAvatar());
                    }
                }
            }
        } else {
            result = desktopService.getDesigner(id);
            if (result == null || result.size() == 0) {
                result = designerRepository.findByShop(id);
            }

            List<String> ids = new ArrayList<>();
            if (result != null && result.size() != 0) {
                for (Designer d : result) {
                    ids.add(d.getId());
                    Shop s = new Shop();
                    s.setId(shop.getId());
                    s.setName(shop.getName());
                    d.setShop(s);
                }
            }
            if (result != null && ids.size() != 0) {
                List<Designer> designers = designerRepository.findByIds(ids);
                for (Designer item : designers) {
                    for (Designer designer : result) {
                        if (item.getId() != null && item.getId().equals(designer.getId())) {
                            if (item.getAvatar() != null) {
                                designer.setAvatar(item.getAvatar());
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public void incReserveCount(String id) {
        designerRepository.incReserveCount(id);
    }

    public void incFansCount(String id) {
        designerRepository.incFansCount(id);
    }

    public void syncDesktopDesigner(Designer designer) {
        Designer item = designerRepository.findOne(designer.getId());
        if (item == null)
            return;

        if (designer.getReserveInfo().getStatus() != null)
            item.getReserveInfo().setStatus(designer.getReserveInfo().getStatus());

        if (designer.getReserveInfo().getStartTime() != null)
            item.getReserveInfo().setStartTime(designer.getReserveInfo().getStartTime());

        if (designer.getReserveInfo().getEndTime() != null)
            item.getReserveInfo().setEndTime(designer.getReserveInfo().getEndTime());

        if (designer.getReserveInfo().getInterval() != 0) {

            item.getReserveInfo().setInterval(designer.getReserveInfo().getInterval());
        }

        if (Assert.isNotNull(designer.getEmpId()))
            item.setEmpId(designer.getEmpId());

        if (designer.getShop() != null)
            if (item.getShop() != null) {
                item.getShop().setId(designer.getShop().getId());
            } else {
                item.setShop(designer.getShop());
            }

        if (Assert.isNotNull(designer.getName()))
            item.setName(designer.getName());

        designerRepository.save(item);
    }
}
