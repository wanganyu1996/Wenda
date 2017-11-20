package com.wenda.service;

import com.wenda.dao.LoginTicketDao;
import com.wenda.dao.UserDao;
import com.wenda.model.LoginTicket;
import com.wenda.model.User;
import com.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wanganyu on 2017/11/11.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;

    public User selectByName(String name){
        return userDao.selectByName(name);
    }
    public Map<String,String> register(String username, String password){
      Map<String,String> map=new HashMap<String,String>();
      if(StringUtils.isBlank(username)){
          map.put("msg","用户名不能为空！");
      }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空！");
        }
        User user=userDao.selectByName(username);
        if(user!=null){
            map.put("msg","用户名已经被注册！");
            return map;
        }
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    public Map<String,String> login(String username, String password){
        Map<String,String> map=new HashMap<String,String>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空！");
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空！");
        }
        User user=userDao.selectByName(username);
        if(user==null){
            map.put("msg","用户名不存在！");
            return map;
        }
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误！");
            return map;
        }
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    private String addLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        Date now=new Date();
        now.setTime(3600*24*100+now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
  public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);

  }

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
