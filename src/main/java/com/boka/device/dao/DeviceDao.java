package com.boka.device.dao;

import com.boka.base.orm.BaseDao;
import com.boka.device.model.Device;

/**
 * Created by boka on 14-12-29.
 */
public interface DeviceDao extends BaseDao {

    public void saveDevice(Device device);

}
