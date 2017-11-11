package com.wenda.service;

import com.wenda.dao.QuestionDao;
import com.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/11.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao  questionDao;
    public List<Question> getLatestQuestions(int userId, int offset, int limit){
     return questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
