package com.wenda.controller;

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
public class FollowController {

    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;
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
           int count=commentService.getCommnetCount(comment.getEntityId(),comment.getEntityType());
           questionService.updateCommentCount(comment.getEntityId(),count);

       } catch (Exception e) {
          logger.error("增加评论失败！");
           e.printStackTrace();
       }
       return "redirect:/question/"+questionId;
   }

}

