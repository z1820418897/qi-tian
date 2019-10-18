package com.zhc.tcp.core.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhc.annotation.LogApi;
import com.zhc.annotation.TcpMapping;
import com.zhc.common.DataBase;
import com.zhc.common.ProtocolMsg;
import com.zhc.common.ProtocolMsgId;
import com.zhc.model.User;
import com.zhc.service.UserService;
import com.zhc.tcp.core.BaseController;
import com.zhc.tcp.core.ControllerCode;
import com.zhc.tcp.protocol.json.FriendsRsp;
import com.zhc.tcp.protocol.json.LoginRsp;
import com.zhc.tcp.protocol.json.MsgRsp;
import com.zhc.tcp.protocol.json.child.Friend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 处理登录
     * */
    @LogApi("CMD_LOGIN_REQ")
    @TcpMapping(cmd = {ProtocolMsgId.CMD_LOGIN_REQ})
    public void loginReq(ProtocolMsg protocolMsg){
        LoginRsp loginRsp=new LoginRsp();

        JSONObject body=(JSONObject)decode(protocolMsg);
        String userName = body.getString("username");
        String password = body.getString("password");
        User user=null;

        try {
            user =userService.findUserByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
        }


        if(user!=null && StringUtils.equals(user.getPassword(),password)){
            if(controllerCallBack!=null){
                controllerCallBack.callBack(ProtocolMsgId.CMD_LOGIN_REQ,ControllerCode.SUCCESS);
            }
            loginRsp.errCode=ControllerCode.SUCCESS.getValue();
            loginRsp.token=buildToken();

            if(DataBase.containsClient(user.getUserId())){
                //还要发条命令告诉获取到的客户端你已经在其他地方登录  然后断开连接
                DataBase.getClient(user.getUserId()).close();
            }
            //将token和对应的id存进redis 这里模拟一下redis
            DataBase.redis.put(loginRsp.token,user.getUserId());
            DataBase.joinClients(user.getUserId(),super.selfClient);

        }else{
            loginRsp.errCode=-1;
        }
        send((short) 1,0,0,ProtocolMsgId.CMD_LOGIN_RSP, JSON.toJSONBytes(loginRsp));

        log.info("解析的包体"+body.toString());
    }


    @LogApi("CMD_FRIENDS_REQ")
    @TcpMapping(cmd= ProtocolMsgId.CMD_FRIENDS_REQ)
    public void friendsReq(ProtocolMsg protocolMsg){
        ArrayList<Friend> friendsInfo = new ArrayList<>();

        Long fromId = DataBase.redis.get(protocolMsg.getFrom());
        List<User> friends=userService.findFriendsByUserId(fromId);

        friends.stream().filter(user -> user!=null).forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                boolean b = DataBase.containsClient(user.getUserId());
                byte state=b?(byte)1:(byte)0;
                Friend friend=new Friend(user.getUserId(),user.getNickName(),state);
                friendsInfo.add(friend);
            }
        });

        FriendsRsp friendsRsp = new FriendsRsp();
        friendsRsp.errCode=0;
        friendsRsp.firends=friendsInfo;

        send((short) 1,0,0,ProtocolMsgId.CMD_FRIENDS_RSP, JSON.toJSONBytes(friendsRsp));

        log.info("收到的消息："+protocolMsg.toString());

    }


    @LogApi("CMD_MSG_REQ")
    @TcpMapping(cmd = ProtocolMsgId.CMD_MSG_REQ)
    public void msgReq(ProtocolMsg protocolMsg){
        long fromToken = protocolMsg.getFrom();
        Long fromId = DataBase.redis.get(fromToken);
        long to = protocolMsg.getTo();

        JSONObject body=(JSONObject)decode(protocolMsg);

        MsgRsp msgRsp = new MsgRsp();
        msgRsp.type=1;
        msgRsp.msg=body.getString("textMsg");

        if(DataBase.containsClient(to)){
            sendOthers((short) 1,fromId,to,ProtocolMsgId.CMD_FRIENDS_RSP, JSON.toJSONBytes(msgRsp));
        }else{
            log.warn(to+":用户不在线");
        }
    }



}
