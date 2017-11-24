package com.wenda.async;


import java.util.List;

/**
 * Created by wanganyu on 2017/11/20.
 */
public interface EventHandler {
    void doHandler(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
