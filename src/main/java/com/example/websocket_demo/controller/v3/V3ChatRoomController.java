package com.example.websocket_demo.controller.v3;

import com.example.websocket_demo.model.InMessage;
import com.example.websocket_demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class V3ChatRoomController {
    @Autowired
    private WebSocketService ws;

    /**
     * 发送私聊
     * @param message
     */
    @MessageMapping("/v3/single/chat")
    public void singleChat(InMessage message){
        ws.sendChatMessage(message);

    }
}
