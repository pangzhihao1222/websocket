package com.example.websocket_demo.config;


import com.example.websocket_demo.interceptor.HttpHandShakeInteceptor;
import com.example.websocket_demo.interceptor.SocketChannelIntecepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {



    /**
     *  基站注册（注册端点，发布或者订阅消息的时候需要连接此端点）
     *  setAllowedOrigins("*")表示允许其它域进行连接
     *  withSockJS()   表示开启sockjs支持
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint-websocket").addInterceptors(new HttpHandShakeInteceptor()).setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理（中介）
     * 服务端推送给客户端的一个路径前缀 registry.enableSimpleBroker("/topic","/chat");
     * 客户端发送数据连接服务端的一个前缀 registry.setApplicationDestinationPrefixes("/app");
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //服务器推送
        registry.enableSimpleBroker("/topic","/chat");
        //客户端连接发送消息到服务器的路劲前缀
        registry.setApplicationDestinationPrefixes("/app");  //将数据发送到/app/v1/chat
    }

    /**
     * 用于实现SocketChannelInteceptor
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());

    }

    /**
     * 用于实现SocketChannelInteceptor
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
    }
}
