package com.boka.user.repository.impl;

import com.boka.common.constant.ProductType;
import com.boka.common.util.Assert;
import com.boka.user.constant.PageConstant;
import com.boka.user.model.Designer;
import com.boka.user.model.Location;
import com.boka.user.repository.DesignerRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

public class DesignerRepositoryImpl implements DesignerRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

	@Override
	public List<Designer> findNearDesigners(Location loc, String city, String keyword, int page) {
		Point point = new Point(loc.getLng(), loc.getLat());
		NearQuery nearQuery = NearQuery.near(point, Metrics.KILOMETERS);
		Query query = new Query(Criteria.where("shop.region.city").is(city).and("product").is(ProductType.FZONE));
		if (Assert.isNotNull(keyword)) {
			query.addCriteria(Criteria.where("name").regex(keyword, "i"));
		}
		nearQuery.query(query);
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

	@Override
	public List<Designer> findCityDesigners(Location loc, String city, String keyword, int page) {
		Query query = new Query(Criteria.where("shop.region.city").is(city).and("product").is(ProductType.FZONE));
		if (Assert.isNotNull(keyword)) {
			query.addCriteria(Criteria.where("name").regex(keyword, "i"));
		}
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("shop.name");
		query.fields().include("level");
		query.fields().include("rank");
		query.fields().include("score");
		query.fields().include("avatar");
		query.fields().include("reservedCnt");
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "level");
		Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "rank");
		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "score");
		Sort sort = new Sort(order, order1, order2);
		Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
		query.with(pageable).with(sort);
		return ops.find(query,Designer.class);
	}

	@Override
	public List<Designer> findCountryDesigners(Location loc, int page) {
		Query query = new Query(Criteria.where("product").is(ProductType.FZONE));
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("shop.name");
		query.fields().include("level");
		query.fields().include("rank");
		query.fields().include("score");
		query.fields().include("avatar");
		query.fields().include("reservedCnt");
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "level");
		Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "rank");
		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "score");
		Sort sort = new Sort(order, order1, order2);
		Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
		query.with(pageable).with(sort);
		return ops.find(query,Designer.class);
	}

	@Override
	public List<Designer> findByShop(String id) {
		Query query = new Query(Criteria.where("product").is(ProductType.FZONE).and("shop._id").is(id));
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("level");
		query.fields().include("rank");
		query.fields().include("score");
		query.fields().include("avatar");
		query.fields().include("reservedCnt");
		query.fields().include("reserveInfo");
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "level");
		Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "rank");
		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "score");
		Sort sort = new Sort(order, order1, order2);
		query.with(sort);
		return ops.find(query,Designer.class);
	}

	@Override
	public List<Designer> findByS3Shop(String custId, String compId) {
		Query query = new Query(Criteria.where("custId").is(custId).and("compId").is(compId));
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("custId");
		query.fields().include("compId");
		query.fields().include("empId");
		return ops.find(query,Designer.class);
	}
}
