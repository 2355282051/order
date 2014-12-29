package com.boka.device.service.impl;

import com.boka.device.dao.DeviceDao;
import com.boka.device.model.Device;
import com.boka.device.service.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by boka on 14-12-18.
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DeviceServiceImpl implements DeviceService {

    private static Logger logger = LogManager.getLogger(DeviceServiceImpl.class.getName());
    private static final Marker SQL_MARKER = MarkerManager.getMarker("SQL");
    private static final Marker SAVE_MARKER = MarkerManager.getMarker("SQL_SAVE");
    private static final Marker UPDATE_MARKER = MarkerManager.getMarker("SQL_UPDATE");
    private static final Marker QUERY_MARKER = MarkerManager.getMarker("SQL_QUERY");

    @Autowired
    private DeviceDao deviceDao;

    @Transactional(readOnly = false, rollbackFor=Throwable.class)
    @Override
    public void saveDevice(Device device) {
        deviceDao.save("saveDevice", device);
        logger.debug(SAVE_MARKER, "saveDevice", device);
    }
}
