package com.wenda.service;

import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by wanganyu on 2017/11/23.
 */
@Service
public class FollowService {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 用户关注了某个实体，可以关注问题，关注用户，关注评论等任何实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean follow(int userId,int entityType,int entityId){
        //粉丝
       String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
       //关注对象
       String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
       Date date=new Date();
       Jedis jedis=jedisAdapter.getJedis();
       Transaction tx=jedisAdapter.multi(jedis);
      //当前用户对这类实体的关注加一
       tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
       //实体的粉丝增加当前用户
       tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return ret.size()==2&&(Long)ret.get(0)>0&&(Long)ret.get(1)>0;
    }

    public boolean unfollow(int userId,int entityType,int entityId){
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Date date=new Date();
        Jedis jedis=jedisAdapter.getJedis();
        Transaction tx=jedisAdapter.multi(jedis);
        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return ret.size()==2&&(Long)ret.get(0)>0&&(Long)ret.get(1)>0;
    }
    private List<Integer> getIdsFromSet(Set<String> idset){
        List<Integer> ids=new ArrayList<>();
        for(String str:idset){
            ids.add(Integer.parseInt(str));
        }
     return ids;
    }
    public List<Integer> getFollowers(int entityType,int entityId,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrange(followerKey,0,count));
    }
    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrange(followerKey,0,count));
    }
    public List<Integer> getFollowees(int userId,int entityType,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,0,count));
    }
    public List<Integer> getFollowees(int userId,int entityType,int offset,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,0,count));
    }
    public long getFolloweeCount(int userId,int entityType){
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return jedisAdapter.zcard(followeeKey);
    }
    public long getFollowerCount(int entityType,int entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(followerKey);
    }

    /**
     * 判断用户是否关注了某个实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean isFollower(int userId,int entityType,int entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zscore(followerKey,String.valueOf(userId))!=null;
    }


}
