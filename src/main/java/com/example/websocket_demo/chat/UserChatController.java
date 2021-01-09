package com.example.websocket_demo.chat;

import com.example.websocket_demo.model.InMessage;
import com.example.websocket_demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserChatController {

    @Autowired
    private WebSocketService ws;

    //模拟数据库用户的数据
    public static Map<String,String> userMap = new HashMap<>();
    static {
        userMap.put("jack","123");
        userMap.put("mary","456");
        userMap.put("tom","789");
        userMap.put("tim","000");
        userMap.put("小D","666");
    }

    //在线用户存储
    public static Map<String,User> onlineUser = new HashMap<>();
    static{
        onlineUser.put("123",new User("admin","888"));
    }

    /**
     * 用户登录功能
     * @param username
     * @param pwd
     * @param session
     * @return
     */
    @RequestMapping(value = "login",method= RequestMethod.POST)
    public String userLogin(@RequestParam(value = "username",required = true)String username, @RequestParam(value = "pwd",required = true)String pwd, @RequestParam(value = "session",required = true)HttpSession session){
         /* 可以设置一个唯一token(雪花算法)，
          用户注册时保存起来
          用户登录时发送给用户
          建立连接时查看用户是否登录了，如果登录了直接返回
         */
        String password = userMap.get(username);
        //1.判断用户密码是否正确
        if(pwd.equals(password)){
            //如果登录成功
            User user = new User(username,pwd);
            //2.存入在线缓存或数据库中
            String sessionId = session.getId();
            onlineUser.put(sessionId,user);
            return "redirect:/v6/chat.html";
        }else {
            return "redirect:/v6/error.html";
        }
    }

    /**
     * 用于定时给客户端推送在线用户
     */
    @Scheduled(fixedRate = 2000)
    public void onlineUser(){
        ws.sendOnlineUser(onlineUser);
    }

    /**
     * 聊天接口
     * @param message
     * @param headerAccessor
     */
    @MessageMapping("/v6/chat")
    public void topicChat(InMessage message, SimpMessageHeaderAccessor headerAccessor){
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        User user = onlineUser.get(sessionId);
        message.setFrom(user.getUsername());
        ws.sendTopicChat(message);
    }

}
