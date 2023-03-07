package com.whn.user_service.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.whn.user_service.domain.Notice;
import com.whn.user_service.po.NoticePo;
import com.whn.user_service.service.NoticeService;
import com.whn.user_service.service.impl.NoticeServiceImpl;
import com.whn.user_service.vo.NoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/5 10:37
 */

@Component
@Slf4j
@ServerEndpoint(value = "/notices/{userId}")
@ConfigurationProperties(prefix = "websocket")
public class WebSocketServer {
    private static NoticeService noticeService;
    @Autowired
    private void setNoticeService(NoticeServiceImpl noticeService) {
        WebSocketServer.noticeService = noticeService;
    }
    /**
     * 记录当前在线连接数
     */
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Value("{websocket.adminId}")
    public String adminId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessionMap.put(userId, session);
        List<NoticeVo> noReceiveNoticeByUserId = noticeService.findNoReceiveNoticeByReceiveUserId(userId);
        if (noReceiveNoticeByUserId.size() > 0) {
            noReceiveNoticeByUserId.forEach(noticeVo -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("from", noticeVo.getUserId());
                jsonObject.put("avatar", noticeVo.getAvatar());
                jsonObject.put("content", noticeVo.getContent());
                jsonObject.put("id", noticeVo.getId());
                sendMessage(jsonObject, userId);
                noticeService.deleteNoticeById(noticeVo.getId());
            });
        }
        log.info("有新用户加入，userId={}, 当前在线人数为：{}", userId, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        log.info("服务端收到用户userId={}的消息:{}", userId, message);
        JSONObject fromMessage = JSON.parseObject(message);
        String toUserId = fromMessage.getString("to");
        String content = fromMessage.getString("content");
        boolean exist = sessionMap.containsKey(toUserId);
        if (exist) {
            Session toSession = sessionMap.get(toUserId);
            this.sendMessage(userId, content, toSession);
            log.info("发送给用户:{}，消息：{}", toUserId, content);
        } else {
            NoticePo notice = NoticePo.builder()
                    .sentUserId(userId)
                    .receiveUserId(toUserId)
                    .content(content)
                    .build();
            noticeService.createNotice(notice);
            log.info("发送失败，用户userId={}不在线，已将信息存储在数据库", toUserId);
        }
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessionMap.remove(userId);
        log.info("有一连接关闭，移除userId={}的用户session, 当前在线人数为：{}", userId, sessionMap.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage(String userId, String content, String toUserId) {
        boolean exist = sessionMap.containsKey(toUserId);
        if (exist) {
            Session toSession = sessionMap.get(toUserId);
            JSONObject toMessage = new JSONObject();
            toMessage.put("from", userId);
            toMessage.put("content", content);
            String message = toMessage.toString();
            try {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("服务端发送消息给客户端失败", e);
            }
        } else {
            NoticePo notice = NoticePo.builder()
                    .sentUserId(userId)
                    .receiveUserId(toUserId)
                    .content(content)
                    .build();
            noticeService.createNotice(notice);
            log.info("发送失败，用户userId={}不在线，已将信息存储在数据库", toUserId);
        }
    }
    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage(JSONObject toMessage, String toUserId) {
        boolean exist = sessionMap.containsKey(toUserId);
        if (exist) {
            Session toSession = sessionMap.get(toUserId);
            String message = toMessage.toString();
            try {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("服务端发送消息给客户端失败", e);
            }
        } else {
            NoticePo notice = NoticePo.builder()
                    .sentUserId(toMessage.getString("userId"))
                    .receiveUserId(toUserId)
                    .content(toMessage.getString("content"))
                    .build();
            noticeService.createNotice(notice);
            log.info("发送失败，用户userId={}不在线，已将信息存储在数据库", toUserId);
        }
    }

    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage( String content, String toUserId) {
        boolean exist = sessionMap.containsKey(toUserId);
        if (exist) {
            Session toSession = sessionMap.get(toUserId);
            JSONObject toMessage = new JSONObject();
            toMessage.put("from", adminId);
            toMessage.put("content", content);
            String message = toMessage.toString();
            try {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("服务端发送消息给客户端失败", e);
            }
        } else {
            NoticePo notice = NoticePo.builder()
                    .sentUserId(adminId)
                    .receiveUserId(toUserId)
                    .content(content)
                    .build();
            noticeService.createNotice(notice);
            log.info("发送失败，用户userId={}不在线，已将信息存储在数据库", toUserId);
        }
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String userId, String content, Session toSession) {
        JSONObject toMessage = new JSONObject();
        toMessage.put("from", userId);
        toMessage.put("content", content);
        String message = toMessage.toString();
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

}

