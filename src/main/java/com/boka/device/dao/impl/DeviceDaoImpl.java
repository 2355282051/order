package com.boka.device.dao.impl;

import com.boka.base.orm.impl.BaseDaoImpl;
import com.boka.device.dao.DeviceDao;
import com.boka.device.model.Device;
import org.springframework.stereotype.Repository;

/**
 * Created by boka on 14-12-29.
 */
@Repository
public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao {

    @Override
    public void saveDevice(Device device) {
        save("saveDevice", device);
    }
}
