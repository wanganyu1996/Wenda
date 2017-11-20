package com.wenda.service;

import com.wenda.dao.MessageDao;
import com.wenda.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/18.
 */
@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveService sensitiveService;
    public int addMessage(Message message){
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message)>0?message.getId():0;
    }
    public List<Message> getConversationDetail(String cnversationId,int offset,int limit){
      return messageDao.getConversationDetail(cnversationId,offset,limit);
    }
    public List<Message> getConversationList(int userId,int offset,int limit){
        return messageDao.getConversationList(userId,offset,limit);
    }
   public  int getConversationUnreadCount(@Param("userId") int userId,@Param("conversationId") String conversationId){
        return messageDao.getConversationUnreadCount(userId,conversationId);
    }
}
