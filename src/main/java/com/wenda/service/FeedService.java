package com.wenda.service;

import com.wenda.dao.FeedDao;
import com.wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/27.
 */
@Service
public class FeedService {
    @Autowired
    FeedDao feedDao;

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count){
        return feedDao.selectUserFeeds(maxId,userIds,count);
    }

    public boolean addFeed(Feed feed){
     feedDao.addFeed(feed);
     return feed.getId()>0;
    }
    public Feed getById(int id){
        return feedDao.getFeedById(id);
    }

}
