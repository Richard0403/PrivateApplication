package com.richard.beautypic.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

    public static int getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

}
