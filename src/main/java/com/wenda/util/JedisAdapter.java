package com.wenda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by wanganyu on 2017/11/19.
 */
@Service
public class JedisAdapter implements InitializingBean{
    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    public static void print(int index,Object obj){
      System.out.println(String.format("%d,%s",index,obj.toString()));
    }
    public static void main(String[] args){
   /*    Jedis jedis=new Jedis("redis://localhost:6379/9");
       jedis.flushDB();
       jedis.set("hello","world");
       print(1,jedis.get("hello"));
       jedis.rename("hello","newhello");
        print(1,jedis.get("newhello"));
        jedis.setex("hello2",15,"world");

        jedis.set("pv","100");
       jedis.incr("pv");
       jedis.incrBy("pv",5);
       print(2,jedis.get("pv"));
        jedis.decrBy("pv",2);
        print(2,jedis.get("pv"));
        print(3,jedis.keys("*"));

        String listName="list";
        jedis.del(listName);
        for(int i=0;i<10;++i){
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(4,jedis.lrange(listName,0,12));
        print(4,jedis.lrange(listName,0,3));

        print(5,jedis.llen(listName));
        print(6,jedis.lpop(listName));
        print(7,jedis.llen(listName));
        print(7,jedis.lrange(listName,2,6));
        print(9,jedis.lindex(listName,3));
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","xxx"));
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE,"a4","bbb"));
        print(11,jedis.lrange(listName,0,12));
        String userKey="user";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","18618181818");
        print(12,jedis.hget(userKey,"name"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14,jedis.hgetAll(userKey));
        print(15,jedis.hexists(userKey,"email"));
        print(16,jedis.hexists(userKey,"age"));
        print(17,jedis.hkeys(userKey));
        print(17,jedis.hvals(userKey));
        jedis.hsetnx(userKey,"school","zju");
        jedis.hsetnx(userKey,"name","yxy");
        print(19,jedis.hgetAll(userKey));

        String likeKeys1="commentLike1";
        String likeKeys2="commentLike2";
        for(int i=0;i<10;++i){
            jedis.sadd(likeKeys1,String.valueOf(i));
            jedis.sadd(likeKeys2,String.valueOf(i*i));
        }
        print(20,jedis.smembers(likeKeys1));
        print(21,jedis.smembers(likeKeys2));
        print(22,jedis.sunion(likeKeys1,likeKeys2));
        print(22,jedis.sdiff(likeKeys1,likeKeys2));
        print(22,jedis.sinter(likeKeys1,likeKeys2));
        print(24,jedis.sismember(likeKeys1,"12"));
        print(24,jedis.sismember(likeKeys2,"16"));
        jedis.srem(likeKeys1,"5");
        print(27,jedis.smembers(likeKeys1));
        jedis.smove(likeKeys2,likeKeys1,"25");
        print(28,jedis.smembers(likeKeys1));
        print(28,jedis.scard(likeKeys1));
        String ranKey="rankKey";
        jedis.zadd(ranKey,15,"Jim");
        jedis.zadd(ranKey,60,"Ben");
        jedis.zadd(ranKey,90,"Lee");
        jedis.zadd(ranKey,75,"Lucy");
        jedis.zadd(ranKey,80,"Mei");
       print(31,jedis.zcard(ranKey));
        print(31,jedis.zcount(ranKey,61,100));
        print(32,jedis.zscore(ranKey,"Lucy"));
        jedis.zincrby(ranKey,2,"Lucy");
        print(33,jedis.zscore(ranKey,"Lucy"));
        jedis.zincrby(ranKey,2,"Luc");
        print(35,jedis.zscore(ranKey,"Luc"));
        print(35,jedis.zrange(ranKey,1,3));
        print(36,jedis.zrevrange(ranKey,1,3));
        for(Tuple tuple:jedis.zrangeByScoreWithScores(ranKey,"60","100")){
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(38,jedis.zrank(ranKey,"Ben"));
        print(39,jedis.zrevrank(ranKey,"Ben"));
        String setKey="zset";
        jedis.zadd(setKey,1,"a");
        jedis.zadd(setKey,1,"b");
        jedis.zadd(setKey,1,"c");
        jedis.zadd(setKey,1,"d");
        jedis.zadd(setKey,1,"e");
        print(40,jedis.zlexcount(setKey,"-","+"));
        print(41,jedis.zlexcount(setKey,"(b","[d"));
        print(42,jedis.zlexcount(setKey,"[b","[d"));
        jedis.zrem(setKey,"b");
        print(42,jedis.zlexcount(setKey,"[b","[d"));
        print(43,jedis.zrange(setKey,0,10));
        jedis.zremrangeByLex(setKey,"(c","+");
        print(44,jedis.zrange(setKey,0,2));

      jedis.set("p","100");
        print(45, jedis.get("p"));
//        JedisPool pool=new JedisPool();//连接池
//        for(int i=0;i<100;i++){
//            try {
//                Jedis j=pool.getResource();
//                j.set("p","100");
//
//                print(45, j.get("p"));
//               // j.close();
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//        }
          User user=new User();
          user.setName("xxx");
          user.setPassword("ppp");
          user.setHeadUrl("a.png");
          user.setSalt("salt");
         user.setId(1);
          jedis.set("user1", JSONObject.toJSONString(user));
       String value=jedis.get("user1");
       User user2= JSONObject.parseObject(value,User.class);
       print(47,user2);*/

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/10");
    }
    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
             jedis=pool.getResource();
          return   jedis.sadd(key,value);
        }catch (Exception e){
          logger.error("发生异常"+e.getMessage());
        }finally {
        if(jedis!=null)
            jedis.close();
        }
         return 0;
    }
    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return   jedis.srem(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }
    public long scard(String key){
        Jedis jedis=null;
        try{
           jedis=pool.getResource();
            return   jedis.scard(key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }
    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return   jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }
    public List<String> brpop(int timeout, String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return  jedis.brpop(timeout,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }
    public long lpush(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return   jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }
    public Jedis getJedis(){
        return pool.getResource();
    }
    public Transaction multi(Jedis jedis){
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }
        return null;
    }
    public List<Object>  exec(Transaction tx,Jedis jedis){
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(tx!=null){
                try {
                    tx.close();
                } catch (IOException e) {
                    logger.error("发生异常"+e.getMessage());
                }
            }
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    public long zadd(String key,double score,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.zadd(key,score,value);
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public Set<String> zrange(String key, int start, int end){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.zrange(key,start,end);
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.zrevrange(key,start,end);
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    public long zcard(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public Double zscore(String key,String member){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.zscore(key,member);
        } catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

}
