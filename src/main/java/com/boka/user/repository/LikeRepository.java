package com.boka.user.repository;

import com.boka.user.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String>, LikeRepositoryAdvance {
	
	public Like findByDesignerIdAndUserId(String designerId, String userId);
}
