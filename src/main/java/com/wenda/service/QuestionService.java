package com.wenda.service;

import com.wenda.dao.QuestionDao;
import com.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/11.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao  questionDao;

    @Autowired
    SensitiveService sensitiveService;

    public Question selectById(int id){
        return questionDao.selectById(id);
    }
    public int addQustion(Question question){
        //敏感词过滤
      question.setContent(HtmlUtils.htmlEscape(question.getContent()));
      question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
      //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        return  questionDao.addQuestion(question)>0?question.getId():0;
    }
    public List<Question> getLatestQuestions(int userId, int offset, int limit){
      return questionDao.selectLatestQuestions(userId,offset,limit);
    }
    public int updateCommentCount(int id,int count){
        return questionDao.updateCommentCount(id,count);
    }
}
