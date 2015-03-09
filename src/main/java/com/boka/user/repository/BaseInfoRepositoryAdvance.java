package com.boka.user.repository;

import com.boka.user.model.User;

public interface BaseInfoRepositoryAdvance {


    public User findByMobile(String mobile, String product);

    public User findByQqId(String qqId);

    public User findByWechatId(String wechatId);

    /**
     * 修改绑定用户状态
     * @param user
     */
    public void updateBindUser(User user);

}
