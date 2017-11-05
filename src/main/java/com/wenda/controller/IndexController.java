package com.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanganyu on 2017/11/03.
 */
@Controller
public class IndexController {
    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(){
        return "Hello Wenda";
    }
    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") int type,
                          @RequestParam(value = "key",defaultValue = "zz",required = false)String key){
        return String.format("Profile Page of %s / %d,t:%d   k:%s",groupId,userId,type,key);
    }
}
