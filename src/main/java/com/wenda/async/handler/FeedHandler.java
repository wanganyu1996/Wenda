package com.wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.EntityType;
import com.wenda.model.Feed;
import com.wenda.model.Question;
import com.wenda.model.User;
import com.wenda.service.*;
import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wanganyu on 2017/11/21.
 */
@Component
public class FeedHandler implements EventHandler{


    @Autowired
    QuestionService questionService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    FollowService followService;

    @Autowired
    JedisAdapter jedisAdapter;

    private String buildFeedData(EventModel eventModel){
      Map<String,String> map=new HashMap<String,String>();
      User actor=userService.getUser(eventModel.getActorId());
      if(actor==null){
          return null;
      }
      map.put("userId",String.valueOf(actor.getId()));
      map.put("userHead",actor.getHeadUrl());
      map.put("userName",actor.getName());
      if(eventModel.getType()==EventType.COMMENT||(eventModel.getType()==EventType.FOLLOW&& eventModel.getEntityType()== EntityType.ENTITY_QUESTION)){
          Question question=questionService.getById(eventModel.getEntityId());
          if(question==null){
              return null;
          }
       map.put("questionId",String.valueOf(question.getId()));
       map.put("questionTitle",question.getTitle());
       return JSONObject.toJSONString(map);
      }
      return null;
    }
    @Override
    public void doHandler(EventModel eventModel) {
      Random r=new Random();
      eventModel.setActorId(1+r.nextInt(10));
      Feed feed=new Feed();
      feed.setCreatedDate(new Date());
      feed.setUserId(eventModel.getActorId());
      feed.setType(eventModel.getType().getValue());
      feed.setData(buildFeedData(eventModel));
      if(feed.getData()==null){
          return;
      }
      feedService.addFeed(feed);
       //给事件的粉丝推
        //获取所有粉丝
        List<Integer> followers=followService.getFollowers(EntityType.ENTITY_USER, eventModel.getActorId(),Integer.MAX_VALUE);
        followers.add(0);
        for(int follower:followers){
          String timeLineKey= RedisKeyUtil.getTimeLineKey(follower);
          jedisAdapter.lpush(timeLineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT,EventType.FOLLOW});
    }
}
