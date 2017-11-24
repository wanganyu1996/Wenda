package com.wenda.async.handler;

import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanganyu on 2017/11/21.
 */
@Component
public class LoginExceptionHandler implements EventHandler{

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandler(EventModel eventModel) {
          Map<String ,Object> map=new HashMap<String,Object>();
          map.put("username", eventModel.getExt("username"));
          mailSender.sendWithHTMLTemplate(eventModel.getExt("email"),"登录IP异常","mail/login_exception.html",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
