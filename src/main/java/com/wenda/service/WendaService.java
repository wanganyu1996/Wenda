package com.wenda.service;

import org.springframework.stereotype.Service;

/**
 * Created by wanganyu on 2017/11/09.
 */
@Service
public class WendaService {
    public String getMessage(int userId){
        return "Hello Message:"+String.valueOf(userId);
    }
}
