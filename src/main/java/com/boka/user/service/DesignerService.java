package com.boka.user.service;

import com.boka.common.util.DistanceUtil;
import com.boka.user.model.Designer;
import com.boka.user.model.Location;
import com.boka.user.repository.BaseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignerService {

	@Autowired
	private BaseInfoRepository baseInfoRepository;
	
	public List<Designer> findNearDesigners(Location loc, String city, int page) {
		return baseInfoRepository.findNearDesigners(loc,city,page);
	}

	public List<Designer> findCityDesigners(Location loc, String city, int page) {
		List<Designer> result = baseInfoRepository.findCityDesigners(loc, city, page);
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
		List<Designer> result = baseInfoRepository.findCountryDesigners(loc, page);
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
}
