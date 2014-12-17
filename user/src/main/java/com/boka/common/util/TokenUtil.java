package com.boka.common.util;

import java.security.MessageDigest;
import java.util.UUID;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;

public class TokenUtil {

	public static String getAccessToken() throws CommonException {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(UUID.randomUUID().toString().getBytes());
            byte[] messageDigest = algorithm.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

            return hexString.toString();
        } catch (Exception e) {
        	throw new CommonException(ExceptionCode.GENERATE_TOKEN_FAILD, e);
        }
    }
	
}
