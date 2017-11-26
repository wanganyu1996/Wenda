package com.wenda.controller;

import com.wenda.model.*;
import com.wenda.service.CommentService;
import com.wenda.service.LikeService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import com.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanganyu on 2017/11/14.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        try {
            Question question=new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
           if(hostHolder.getUser()==null){
              question.setUserId(WendaUtil.ANONYMOUS_USERID);
               //return WendaUtil.getJSONString(999);
           }else{
               question.setUserId(hostHolder.getUser().getId());
           }
            if(questionService.addQustion(question)>0){
               return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("添加问题失败"+e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");
  }
    @RequestMapping(value="/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        List<Comment> commentList=commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments=new ArrayList<ViewObject>();
        for(Comment comment:commentList){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser()==null){
                vo.set("liked",0);
            }else{
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);

        }
        Question question=questionService.getById(qid);
        model.addAttribute("comments",comments);
         model.addAttribute("question",question);
       model.addAttribute("user",userService.getUser(question.getUserId()));
        return "detail";
    }
}
