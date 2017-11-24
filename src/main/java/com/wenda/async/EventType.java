package com.wenda.async;

/**
 * Created by wanganyu on 2017/11/20.
 */
public  enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);
    private int value;
   EventType(int value){
       this.value=value;
   }
   public int getValue(){
       return value;
   }
}