package com.wenda.async.handler;

import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.EntityType;
import com.wenda.model.Message;
import com.wenda.model.User;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import com.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wanganyu on 2017/11/21.
 */
@Component
public class FollowHandler implements EventHandler{

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());
        if (eventModel.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName() + "关注了你的问题，http://127.0.0.1:8080/question/" + eventModel.getExt("questionId"));
            messageService.addMessage(message);
        }else if(eventModel.getEntityType() == EntityType.ENTITY_USER){
            message.setContent("用户" + user.getName() + "关注了,http://127.0.0.1:8080/user/" + eventModel.getActorId());
            messageService.addMessage(message);
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
