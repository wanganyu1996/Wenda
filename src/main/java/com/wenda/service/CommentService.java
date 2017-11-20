package com.wenda.service;

import com.wenda.dao.CommentDao;
import com.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/17.
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitiveService sensitiveService;
    public List<Comment> getCommentsByEntity(int entityId,int entityType){
       return commentDao.selectCommentByEntity(entityId,entityType);
    }
    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
       return  commentDao.addComment(comment)>0?comment.getId():0;
    }
    public int getCommnetCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }
    public boolean deleteComment(int commentId){
        return commentDao.updateStatus(commentId,1)>0;
    }
}
