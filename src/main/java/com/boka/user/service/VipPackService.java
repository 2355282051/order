package com.boka.user.service;

import com.boka.user.mapper.VipPackMapper;
import com.boka.user.model.VipPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class VipPackService {

    @Autowired
    private VipPackMapper vipPackMapper;

    /**
     * 添加会员套餐
     *
     * @param vipPack
     * @return
     * @throws com.boka.common.exception.CommonException
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void add(VipPack vipPack) {
        vipPackMapper.saveVipPack(vipPack);
    }

    /**
     * 获取所有会员套餐
     *
     * @return
     * @throws com.boka.common.exception.CommonException
     */
    public List<VipPack> getAllVipPack() {
        return vipPackMapper.queryAllVipPack();
    }

    /**
     * 根据ID查看 会员套餐
     * @param vipPackId
     * @return
     */
    public VipPack getVipPackById(String vipPackId) {
        return vipPackMapper.queryVipPackById(vipPackId);
    }

}
