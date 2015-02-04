package com.boka.user.service;

import com.boka.common.util.DistanceUtil;
import com.boka.user.dto.DesignerTO;
import com.boka.user.model.Designer;
import com.boka.user.model.DesignerStar;
import com.boka.user.model.Location;
import com.boka.user.model.Shop;
import com.boka.user.repository.DesignerRepository;
import com.boka.user.repository.DesignerStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DesignerService {

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private DesignerStarRepository designerStarRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private S3UserService s3UserService;

    @Autowired
    private DesktopService desktopService;

    public List<Designer> findNearDesigners(Location loc, String city, String keyword, int page) {
        if (loc.getLat() == null || loc.getLng() == null)
            return findCityDesigners(loc, city, keyword, page);
        else
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

    public List<Designer> findCountyDesigners(Location loc, int page) {
        List<Designer> result = designerRepository.findCountryDesigners(loc, page);
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

    public DesignerTO getUserInfo(String id) {
        Designer bean = designerRepository.findOne(id);
        if (bean == null) {
            return null;
        }
        DesignerTO result = new DesignerTO(bean);
        result.setMobile(bean.getMobile());
        return result;
    }

    public List<DesignerStar> getDesignerStar(String city) {
        Sort sort = new Sort(Sort.Direction.ASC, "index");
        List<DesignerStar> result = designerStarRepository.findByCity(city, sort);
        if (result != null && result.size() == 4) {
            return result;
        }else if (result == null){
            result = designerStarRepository.findByCity("310000", sort);
        }else if (result.size() < 4){
            List<DesignerStar> other = designerStarRepository.findByCity("310000", sort);
            for (int i = 0; i<4 - result.size(); i++) {
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
            return result;
        }else {
            result = desktopService.getDesigner(id);
            if (result == null || result.size() == 0) {
                result = designerRepository.findByShop(id);
            }
        }
        return result;
    }

    public void incReserveCount(String id) {
        designerRepository.incReserveCount(id);
    }
}
