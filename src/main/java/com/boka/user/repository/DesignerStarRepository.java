package com.boka.user.repository;

import com.boka.user.model.DesignerStar;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DesignerStarRepository extends MongoRepository<DesignerStar, String> {

    public List<DesignerStar> findByCity(String city, Sort sort);
}
