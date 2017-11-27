package com.wenda.controller;

import com.wenda.model.EntityType;
import com.wenda.model.Feed;
import com.wenda.model.HostHolder;
import com.wenda.service.FeedService;
import com.wenda.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanganyu on 2017/11/27.
 */
@Controller
public class FeedController {

    @Autowired
    FeedService feedService;

    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;
    @RequestMapping(path={"/pullfeeds"},method={RequestMethod.GET})
    public String getPullFeeds(Model model){
       int localUserId=hostHolder.getUser()==null?0:hostHolder.getUser().getId();
       List<Integer> followees=new ArrayList<>();
       if(localUserId!=0){
          followees=followService.getFollowees(localUserId, EntityType.ENTITY_USER,Integer.MAX_VALUE);
       }
       List<Feed> feeds=feedService.getUserFeeds(Integer.MAX_VALUE,followees,10);
       model.addAttribute("feeds",feeds);
        return "feeds";
    }
}
