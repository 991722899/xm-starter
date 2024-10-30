package com.xm.starter.socket.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.base.util.Assert;
import com.xm.starter.socket.model.SocketEventMsg;
import com.xm.starter.socket.model.SocketUser;
import com.xm.starter.socket.model.SocketUserChannel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SocketUtil {
    //记录channel id和channel对象的映射关系(连接认证成功后记录)
    private static final ConcurrentHashMap<String, SocketUserChannel> channelMap = new ConcurrentHashMap<>();
    //记录用户id和用户对象的映射关系(连接认证成功后记录)
    private static final ConcurrentHashMap<String, Map<String,SocketUser>> userMap = new ConcurrentHashMap<>();


    public static void addChannel(String channelId,ChannelHandlerContext context,SocketUser socketUser){
        SocketUserChannel socketUserChannel = new SocketUserChannel();
        socketUserChannel.setChannelId(channelId);
        socketUserChannel.setChannelHandlerContext(context);
        socketUserChannel.setUserId(socketUser.getUserId());
        channelMap.put(channelId,socketUserChannel);

        Map<String,SocketUser> socketUserMap = userMap.get(socketUser.getUserId());
        if(CollUtil.isEmpty(socketUserMap)){
            socketUserMap = new ConcurrentHashMap<>();
           userMap.put(socketUser.getUserId(),socketUserMap);
        }
        socketUserMap.put(channelId,socketUser);
    }

    public static void removeChannel(String channelId){
        SocketUserChannel socketUserChannel = channelMap.get(channelId);
        if(socketUserChannel!=null){
            Map<String,SocketUser> socketUserMap = userMap.get(socketUserChannel.getUserId());
            if(CollUtil.isNotEmpty(socketUserMap)){
                socketUserMap.remove(channelId);
            }
        }
        channelMap.remove(channelId);
    }

    public static List<SocketUser> getUsers(){
        return userMap.values().stream().flatMap(map->map.values().stream()).collect(Collectors.toList());
    }

    /**
     * 发送消息
     * @param userId
     * @param msg
     */
    public static void send(String userId, SocketEventMsg msg) throws JsonProcessingException {
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        Assert.isTrue(userMap.containsKey(userId) && !userMap.get(userId).isEmpty(),"用户不在线");
        for (String s : userMap.get(userId).keySet()) {
            if(channelMap.containsKey(s)){
                channelMap.get(s).getChannelHandlerContext().writeAndFlush(objectMapper.writeValueAsString(msg));
            }
        }
    }
}
