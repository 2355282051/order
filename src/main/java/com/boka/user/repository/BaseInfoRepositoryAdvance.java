package com.boka.user.repository;

import com.boka.user.model.User;

import java.util.List;

public interface BaseInfoRepositoryAdvance {


    public User findByMobile(String mobile, String product);

    public User findByQqId(String qqId);

    public User findByWechatId(String wechatId);

    /**
     * 修改绑定用户状态
     * @param user
     */
    public void updateBindUser(User user);


    public List<User> findUserByOpenId(String openId);

}
