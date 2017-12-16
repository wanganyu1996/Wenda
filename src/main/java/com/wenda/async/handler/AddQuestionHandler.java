package com.wenda.async.handler;

import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanganyu on 2017/12/09.
 */
@Component
public class AddQuestionHandler implements EventHandler{
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);
    @Autowired
    SearchService searchService;

    @Override
    public void doHandler(EventModel eventModel) {
        try {
            searchService.indexQuestion(eventModel.getEntityId(),eventModel.getExt("title"), eventModel.getExt("content"));
        } catch (Exception e) {
           logger.error("增加题目索引失败");
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
