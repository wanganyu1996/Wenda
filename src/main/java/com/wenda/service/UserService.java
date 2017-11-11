package com.wenda.service;

import com.wenda.dao.UserDao;
import com.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanganyu on 2017/11/11.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getUser(int id){
        return userDao.selectById(id);
    }
}
