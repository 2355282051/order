package com.boka.user.controller;

import com.boka.common.util.UUIDGenerator;
import com.boka.device.model.Device;
import com.boka.device.service.DeviceService;
import com.boka.user.dto.DeviceTO;
import com.boka.user.dto.ResultTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by boka on 14-12-29.
 */
@RestController
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public ResultTO addDevice(@RequestBody DeviceTO deviceDTO) {
        ResultTO result = new ResultTO();
        try {
            Device device = new Device();
            device.setId(UUIDGenerator.getUUID());
            device.setDeviceId(deviceDTO.getDeviceId());
            device.setPhoneType(deviceDTO.getPhoneType());
            device.setPhone(deviceDTO.getPhone());
            device.setPhoneVersion(deviceDTO.getPhoneVersion());
            device.setAppName(deviceDTO.getAppName());
            device.setAppVersion(deviceDTO.getAppVersion());
            device.setUserId(deviceDTO.getUserId());
            device.setUserName(device.getUserName());
            deviceService.saveDevice(device);
            result.setResult("添加成功");
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }
}
