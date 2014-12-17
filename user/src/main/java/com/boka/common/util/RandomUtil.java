package com.boka.common.util;

import java.util.Random;

public class RandomUtil {

	public static String randomSalt() {
		String val = "";  
        Random random = new Random();  
        for(int i = 0; i < 8; i++) {  
        	val += (char)(random.nextInt(93) + 33);
        }  
        return val;
	}
	
}
