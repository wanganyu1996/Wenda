package com.wenda.controller;

import com.wenda.model.EntityType;
import com.wenda.model.Question;
import com.wenda.model.ViewObject;
import com.wenda.service.FollowService;
import com.wenda.service.QuestionService;
import com.wenda.service.SearchService;
import com.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanganyu on 2017/11/17.
 */
@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;



    @Autowired
    QuestionService questionService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;


   @RequestMapping(path={"/search"},method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                             @RequestParam(value = "offset",defaultValue = "0") int offset,
                             @RequestParam(value = "count",defaultValue = "10") int count
                             ){
       try {
           List<Question> questionList=searchService.serachQuestion(keyword,offset,count,"<em>","</em>");
           List<ViewObject> vos=new ArrayList<ViewObject>();
           for(Question question:questionList){
              //注释
               Question q=questionService.getById(question.getId());
               ViewObject vo=new ViewObject();
               if(question.getContent()!=null){
                   q.setContent(question.getContent());
               }
               if(question.getTitle()!=null){
                   q.setTitle(question.getTitle());
               }
               vo.set("question", q);
               vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
               vo.set("user",userService.getUser(q.getUserId()));
               vos.add(vo);
           }
           model.addAttribute("vos",vos);
           model.addAttribute("keyword",keyword);
       } catch (Exception e) {
          logger.error("搜索评论失败！");
           e.printStackTrace();
       }
       return "result";
   }

}

