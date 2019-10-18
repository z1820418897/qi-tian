package com.zhc.common;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static final Map<Long,Channel> clients=new HashMap();

    public static void joinClients(Long id, Channel client){
        clients.put(id,client);
    }
    public static void removeClients(Long id){

        clients.remove(id);
    }
    public static Channel getClient(Long id){
        return clients.get(id);
    }
    public static boolean containsClient(Long id){
        return clients.containsKey(id);
    }

    public static final Map<Long,Long> redis=new HashMap();


}
