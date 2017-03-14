package me.supercube.common.util;

import java.util.UUID;

/**
 * 生产uuid
 * Created by wangdz on 2017/3/14.
 */
public class UUIDUtils {

    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象
        return uuid.replace("-", "");
    }

}
