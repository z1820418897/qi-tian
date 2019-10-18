package com.zhc.tcp.core;

import com.alibaba.fastjson.JSONObject;
import com.zhc.common.DataBase;
import com.zhc.common.ProtocolMsg;
import com.zhc.tcp.tools.MsgTool;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户端发送的消息处理  利用反射在服务器启动的时候把命令对应的方法放入进去
 * 这里用springboot弄了个注解 扫描注解 会自动将带注解方法加入进来
 * */
public class BaseController {

    public static Map<Integer, Method> methodMap = new HashMap<>();


    public ControllerCallBack controllerCallBack=null;//回调
    public void setOnControllerCallBack(ControllerCallBack controllerCallBack){
        this.controllerCallBack=controllerCallBack;
    }

    public Channel selfClient;
    public void setSelfClient(Channel channel){
        this.selfClient=channel;
    }

    /**
     *根据协议格式解码协议包 这里先使用json格式
     * */
    public Object decode(ProtocolMsg protocolMsg){

        if(protocolMsg.getProtocolId()==1){
            JSONObject jsonObject = MsgTool.buildBody(protocolMsg.getBody());

            return jsonObject;
        }

        return null;
    }

    /**
     * 回复消息
     * */

    public void send(short protocolId,long from,long to,int msgId,byte[] body){
        ProtocolMsg data=new ProtocolMsg();
        data.setBody(body);
        data.setLength(body.length);
        data.setMsgId(msgId);
        data.setFrom(from);
        data.setTo(to);
        data.setProtocolId(protocolId);
        selfClient.writeAndFlush(data);
    }

    /**
     * 发送消息
     * */
    public void sendOthers(short protocolId,long from,long to,int msgId,byte[] body){
        ProtocolMsg data=new ProtocolMsg();
        data.setBody(body);
        data.setLength(body.length);
        data.setMsgId(msgId);
        data.setFrom(from);
        data.setTo(to);
        data.setProtocolId(protocolId);

        DataBase.getClient(to).writeAndFlush(data);
    }


    /**
     * token生产
     * */
    public long buildToken() {

        Random random = new Random();
        int s = random.nextInt(10000)%(10000-1000+1) + 1000;

        long token=Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date())) * s;

        return token;
    }
//    static {
//        Class<ServiceCore> clazz = ServiceCore.class;
//        Method login_req =null;
//
//        try {
//            login_req = clazz.getMethod("login_req", UserController.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        methodMap.put(CMD_LOGIN_REQ.getValue(),login_req);
//    }
}

