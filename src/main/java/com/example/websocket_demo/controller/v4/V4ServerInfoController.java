package com.example.websocket_demo.controller.v4;

import com.example.websocket_demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * 实现推送服务器的JVM负载，已用内存等消息
 */
@Controller
public class V4ServerInfoController {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/v4/schedule/push")
    @Scheduled(fixedRate = 3000)  //定时执行
    public void sendServerInfo(){
        ws.sendServerInfo();
    }

}
