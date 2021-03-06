package com.example.websocket_demo.controller.v2;

import com.example.websocket_demo.model.InMessage;
import com.example.websocket_demo.model.OutMessage;
import com.example.websocket_demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class V2GameInfoController {


    @Autowired
    private WebSocketService ws;

    /**
     * 将数据发送到/app/v1/chat
     * 然后转发到/topic/game_chat
     * @MessageMapping("/v1/chat") 针对websocket去使用
     * @SendTo("/topic/game_chat") 发送给订阅的客户
     * 缺点：sendTo不通用，固定发送给指定的订阅者
     * 所以可以使用simpleMessageTemplate 支持多种发送
     * @param message
     * @return
     */
    @MessageMapping("/v2/chat")
    //@SendTo("/topic/game_chat")
    public void gameInfo(String dest,InMessage message){
        ws.sendTopicMessage("/topic/game_rank",message);
    }
}
