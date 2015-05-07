package com.boka.user.repository.impl;

import com.boka.common.constant.PageConstant;
import com.boka.common.constant.ProductType;
import com.boka.common.util.Assert;
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
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

public class DesignerRepositoryImpl implements DesignerRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

	@Override
	public List<Designer> findNearDesigners(Location loc, String city, String keyword, int page) {
		Point point = new Point(loc.getLng(), loc.getLat());
		NearQuery nearQuery = NearQuery.near(point, Metrics.KILOMETERS);
		Criteria criteria = Criteria.where("shop.region.city").is(city).and("product").is(ProductType.FZONE).and("workCount").gt(0);
		if (Assert.isNotNull(keyword)) {
			criteria.and("name").regex(keyword, "i");
		}

		Query query = new Query(criteria);

		nearQuery.query(query);
		Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
		nearQuery.with(pageable);
		GeoResults<Designer> list = ops.geoNear(nearQuery, Designer.class);
		List<Designer> result = new ArrayList<>();
		for (GeoResult<Designer> item : list) {

			Designer dbDesigner = item.getContent();

			Designer designer = new Designer();
			designer.setId(dbDesigner.getId());
			designer.setDistance(item.getDistance().getValue());
			designer.setAvatar(dbDesigner.getAvatar());
			designer.setSex(dbDesigner.getSex());
			designer.setAddress(dbDesigner.getAddress());
			designer.setRegion(dbDesigner.getRegion());
			designer.setLevel(dbDesigner.getLevel());
			designer.setName(dbDesigner.getName());
			designer.setReserveCnt(dbDesigner.getReserveCnt());
			designer.setShop(dbDesigner.getShop());
			designer.setScore(dbDesigner.getScore());
			designer.setHaircutPrice(dbDesigner.getHaircutPrice());
			designer.setModelingPrice(dbDesigner.getModelingPrice());
			result.add(designer);
		}
		return result;
	}

	@Override
	public List<Designer> findCityDesigners(Location loc, String city, String keyword, int page) {

		Criteria criteria = Criteria.where("shop.region.city").is(city).and("product").is(ProductType.FZONE);
		if (Assert.isNotNull(keyword)) {
			criteria.and("name").regex(keyword, "i");
		}
		Query query = new Query(criteria);
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("shop.name");
		query.fields().include("level");
		query.fields().include("rank");
		query.fields().include("score");
		query.fields().include("avatar");
		query.fields().include("reservedCnt");
		query.fields().include("grade");
		query.fields().include("haircutPrice");
		query.fields().include("modelingPrice");
		query.fields().include("shampooPrice");
		query.fields().include("commentCount");
		query.fields().include("likeCount");
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
		query.fields().include("grade");
		query.fields().include("haircutPrice");
		query.fields().include("modelingPrice");
		query.fields().include("shampooPrice");
		query.fields().include("commentCount");
		query.fields().include("likeCount");
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
		query.fields().include("avatar");
		return ops.find(query,Designer.class);
	}

	@Override
	public void incReserveCount(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("reservedCnt", 1);
		ops.updateFirst(query, update, Designer.class);
	}

	@Override
	public List<Designer> findByIds(List<String> ids) {
		Query query = new Query(Criteria.where("_id").in(ids));
		return ops.find(query, Designer.class);
	}

	@Override
	public void incFansCount(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("fansCount", 1);
		ops.updateFirst(query, update, Designer.class);
	}

	@Override
	public void incLikeCount(String id, int num) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("likeCount", num);
		ops.updateFirst(query, update, Designer.class);
	}
}
