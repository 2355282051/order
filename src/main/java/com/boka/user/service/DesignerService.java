package com.boka.user.service;

import com.boka.common.util.DistanceUtil;
import com.boka.user.dto.DesignerTO;
import com.boka.user.model.Designer;
import com.boka.user.model.DesignerStar;
import com.boka.user.model.Location;
import com.boka.user.repository.DesignerRepository;
import com.boka.user.repository.DesignerStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignerService {

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private DesignerStarRepository designerStarRepository;

    public List<Designer> findNearDesigners(Location loc, String city, int page) {
        return designerRepository.findNearDesigners(loc, city, page);
    }

    public List<Designer> findCityDesigners(Location loc, String city, int page) {
        List<Designer> result = designerRepository.findCityDesigners(loc, city, page);
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
        return result;
    }

    public List<DesignerStar> getDesignerStar(String city) {
        Sort sort = new Sort(Sort.Direction.ASC, "index");
        return designerStarRepository.findByCity(city, sort);
    }
}
