package com.boka.user.repository;


import com.boka.user.model.Like;

import java.util.List;

public interface LikeRepositoryAdvance {

	public List<Like> findInIds(String userId, List<String> ids);

	public Like findById(String userId, String id);
}
