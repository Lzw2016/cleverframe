package org.cleverframe.sso.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/6 13:11 <br/>
 */
public class MyHandler extends TextWebSocketHandler {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(MyHandler.class);

    private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

    //接收文本消息，并发送出去
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("接收文本消息，并发送出去");
        super.handleTextMessage(session, message);
        TextMessage result = new TextMessage("[服务端返回数据]" + message.getPayload());
        session.sendMessage(result);
    }

    //连接建立后处理
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("连接建立后处理");
        sessions.add(session);
        //处理离线消息
    }

    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("抛出异常时处理");
        sessions.remove(session);
    }

    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("连接关闭后处理");
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}