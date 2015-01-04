package com.boka.user.repository;

import com.boka.user.model.Designer;
import com.boka.user.model.Location;

import java.util.List;

public interface BaseInfoRepositoryAdvance {

	public List<Designer> findNearDesigners(Location loc, String city, int page);
	
}
