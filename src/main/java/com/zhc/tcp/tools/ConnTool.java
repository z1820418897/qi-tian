package com.zhc.tcp.tools;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConnTool {

    private static Map<Long,Channel> clients=new HashMap();

    public static Map get(){
        return clients;
    }

    //获取客户端
    public static Channel getClient(Long token){
        return clients.get(token);
    }

    public static void addClient(Long token,Channel client){
        clients.put(token,client);
    }

    public static void removeClient(Channel client){
        Collection<Channel> values = clients.values();
        values.remove(client);
    }

    //获取连接数量
    public static Integer getConnCount(){
        return clients.size();
    }

}
