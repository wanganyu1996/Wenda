package com.wenda.controller;

import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import com.wenda.model.Comment;
import com.wenda.model.EntityType;
import com.wenda.model.HostHolder;
import com.wenda.service.CommentService;
import com.wenda.service.QuestionService;
import com.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by wanganyu on 2017/11/17.
 */
@Controller
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;
   @RequestMapping(path={"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String  content){
       try {
           Comment comment=new Comment();
           comment.setContent(content);
           if(hostHolder.getUser()!=null){
               comment.setUserId(hostHolder.getUser().getId());
           }else{
               comment.setUserId(WendaUtil.ANONYMOUS_USERID);
               //return "redirect:/";
           }
           comment.setCreatedDate(new Date());
           comment.setEntityType(EntityType.ENTITY_QUESTION);
           comment.setEntityId(questionId);
           commentService.addComment(comment);
           int count=commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
           questionService.updateCommentCount(comment.getEntityId(),count);
           eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                   .setEntityId(questionId));
       } catch (Exception e) {
          logger.error("增加评论失败！");
           e.printStackTrace();
       }
       return "redirect:/question/"+questionId;
   }

}

