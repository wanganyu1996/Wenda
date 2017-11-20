package com.wenda.util;

/**
 * Created by wanganyu on 2017/11/20.
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DSILIKE="DISLIKE";

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+String.valueOf(entityId);
    }
    public static String getDisLikeKey(int entityType,int entityId){
        return BIZ_DSILIKE+SPLIT+String.valueOf(entityType)+String.valueOf(entityId);
    }

}
