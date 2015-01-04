package com.boka.user.service;

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
}
