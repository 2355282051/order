package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.user.model.Like;
import com.boka.user.repository.DesignerRepository;
import com.boka.user.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private DesignerRepository designerRepository;
	

	public void like(String designerId, String userId) {
		Like like = likeRepository.findByDesignerIdAndUserId(designerId, userId);
		if(like == null) {
			Like bean = new Like();
			bean.setDesignerId(designerId);
			bean.setUserId(userId);
			bean.setLikeDate(Calendar.getInstance().getTime());
			likeRepository.save(bean);
			//喜欢数增加
			designerRepository.incLikeCount(designerId, 1);
		} else {
			throw new CommonException("您已点过赞");
		}
	}

	public void cancelLike(String designerId, String userId) {
		Like like = likeRepository.findByDesignerIdAndUserId(designerId, userId);
		if(like != null)
		{
			likeRepository.delete(like.getId());
			//喜欢数减少
			designerRepository.incLikeCount(designerId, -1);
		} else {
			throw new CommonException("您已取消点赞");
		}
	}

	
}
