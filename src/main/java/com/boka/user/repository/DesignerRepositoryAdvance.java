package com.boka.user.repository;

import com.boka.user.model.Designer;
import com.boka.user.model.Location;

import java.util.List;

public interface DesignerRepositoryAdvance {

	public List<Designer> findNearDesigners(Location loc, String city, String keyword, int page);

	public List<Designer> findCityDesigners(Location loc, String city, String keyword, int page);

	public List<Designer> findCountryDesigners(Location loc, int page);

	public List<Designer> findByShop(String id);

	public List<Designer> findByS3Shop(String custId, String compId);

	public void incReserveCount(String id);

	public List<Designer> findByIds(List<String> ids);

	public void incFansCount(String id);

	public void incLikeCount(String id, int num);
}
