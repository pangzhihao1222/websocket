package com.example.websocket_demo.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 功能描述：http握手拦截器，可以通过这个类的方法获取request和response
 */
public class HttpHandShakeInteceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        System.out.println("握手拦截器beforeHandshake");
        if(serverHttpRequest instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletServerHttpRequest =  (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
            String sessionId = session.getId();
            System.out.println("握手拦截器beforeHandshake sessionId="+sessionId);
            map.put("sessionId",sessionId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手拦截器afterHandshake");

    }
}
