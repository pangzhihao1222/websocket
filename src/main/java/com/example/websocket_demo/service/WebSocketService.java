package com.example.websocket_demo.service;

import com.example.websocket_demo.chat.User;
import com.example.websocket_demo.model.InMessage;
import com.example.websocket_demo.model.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 简单消息模板，用来推送消息
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 发送给前端20次消息
     * @param dest
     * @param inMessage
     */
    public void sendTopicMessage(String dest,InMessage inMessage){
        for(int i=0;i<20;i++){
            template.convertAndSend(dest,new OutMessage(inMessage.getContent()+i));
        }

    }

    /**
     * 启动私聊模式可以发送到不同的主题
     * message.getTo可以实现内容唯一，并发送过去实现私聊
     * @param message
     */
    public void sendChatMessage(InMessage message) {

        template.convertAndSend("/chat/single"+message.getTo(),
                new OutMessage(message.getFrom()+"发送："+message.getContent()));
    }

    /**
     * 实现jvm可用系统参数的获取并推送给客户端
     */
    public void sendServerInfo() {
        //获取jvm可用核数
        int processors = Runtime.getRuntime().availableProcessors();
        long freeMomory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        String message = String.format("服务器可用处理器:%s; 虚拟机空闲内存大小:%s; 最大内存大小:%s",processors,freeMomory,maxMemory);
        template.convertAndSend("/topic/server_info");
    }

    /**
     * 定时给客户推送在线用户（也可以实时推送在断开连接的时候发送）
     * @param onlineUser
     */
    public void sendOnlineUser(Map<String, User> onlineUser) {
        String msg = "";
        for(Map.Entry<String,User> entry:onlineUser.entrySet()){
            msg = msg.concat(entry.getValue().getUsername()+" || ");
        }
        //发送的消息一定是对象
        template.convertAndSend("/topic/onlineuser",new OutMessage(msg));
    }

    /**
     * 用户多人聊天
     * @param message
     */
    public void sendTopicChat(InMessage message) {
        String msg = message.getFrom()+"发送："+message.getContent();
        template.convertAndSend("/topic/chat",new OutMessage(msg));
    }
}
