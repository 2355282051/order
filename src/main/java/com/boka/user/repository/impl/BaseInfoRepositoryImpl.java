package com.boka.user.repository.impl;

import com.boka.user.constant.PageConstant;
import com.boka.user.model.Designer;
import com.boka.user.model.Location;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BaseInfoRepositoryImpl implements BaseInfoRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

	@Override
	public List<Designer> findNearDesigners(Location loc, String city, int page) {
		Point point = new Point(loc.getLng(), loc.getLat());
		NearQuery nearQuery = NearQuery.near(point, Metrics.KILOMETERS);
		nearQuery.query(new Query(Criteria.where("region.city").is(city).and("product").is("fzone")));
		Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
		nearQuery.with(pageable);
		GeoResults<Designer> list = ops.geoNear(nearQuery, Designer.class);
		List<Designer> result = new ArrayList<>();
		for (GeoResult<Designer> item : list) {
			Designer user = new Designer();
			user.setId(item.getContent().getId());
			user.setDistance(item.getDistance().getValue());
			user.setAvatar(item.getContent().getAvatar());
			user.setSex(item.getContent().getSex());
			user.setAddress(item.getContent().getAddress());
			user.setRegion(item.getContent().getRegion());
			user.setLevel(item.getContent().getLevel());
			user.setName(item.getContent().getName());
			user.setReservedCnt(item.getContent().getReservedCnt());
			user.setShop(item.getContent().getShop());
			user.setScore(item.getContent().getScore());
			result.add(user);
		}
		return result;
	}
}
