package com.boka.user.repository;

import com.boka.user.model.Designer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DesignerRepository extends MongoRepository<Designer, String> {

}
