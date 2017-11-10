package com.wenda.controller;

import com.wenda.model.User;
import com.wenda.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by wanganyu on 2017/11/03.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    WendaService wendaService;
    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession){
        logger.info("Index 方法");
        return wendaService.getMessage(0)+"Hello Wenda"+httpSession.getAttribute("msg");
    }
    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") int type,
                          @RequestParam(value = "key",defaultValue = "zz",required = false)String key){
        return String.format("Profile Page of %s / %d,t:%d   k:%s",groupId,userId,type,key);
    }

    @RequestMapping(path = {"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","vvvvv");
//        List<String> colors= Arrays.asList(new String[]{"RED","GREEN","BLUE"});
//        model.addAttribute("colors",colors);
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);
        Map<String,String> map=new HashMap<>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map", map);
        model.addAttribute("user",new User("Tom"));
        return "home";
    }

    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,HttpServletResponse response
                          , HttpServletRequest request,HttpSession httpSession,@CookieValue("JSESSIONID") String sessionId){
       StringBuilder sb=new StringBuilder();
       sb.append("COOKIEVALUE:"+sessionId+"<br>");
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        sb.append("<br><br><br>");
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
               sb.append("<br>Cookie: "+cookie.getName()+"----value:"+cookie.getValue());
            }
        }

       sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        response.addHeader("nowcoderId","hello");
        response.addCookie(new Cookie("wang","hello"));
      return sb.toString();
    }

   /* @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    public String redirect(@PathVariable("code")int code,HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
       return "redirect:/";
    }*/
    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code")int code,HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red=new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
       if("admin".equals(key)){
           return "hello admin";
       }
       throw new IllegalArgumentException("参数不对");
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }
}
