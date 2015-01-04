package com.boka.user.repository.impl;

import com.boka.common.constant.ProductType;
import com.boka.user.constant.PageConstant;
import com.boka.user.model.Designer;
import com.boka.user.model.Location;
import com.boka.user.repository.BaseInfoRepositoryAdvance;
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

public class BaseInfoRepositoryImpl implements BaseInfoRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

}
