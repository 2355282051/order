package com.boka.user.mapper;

import com.boka.user.model.VipPack;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by boka on 15-3-11.
 */
@Repository
public interface VipPackMapper {

    public void saveVipPack(VipPack vipPack);

    public List<VipPack> queryAllVipPack();

    public VipPack queryVipPackById(@Param("vipPackId")String vipPackId);
}
