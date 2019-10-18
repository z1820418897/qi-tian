package com.zhc.tcp;

import com.alibaba.fastjson.JSONObject;
import com.zhc.common.ProtocolMsg;
import com.zhc.common.ProtocolMsgId;
import com.zhc.tcp.core.BaseController;
import com.zhc.tcp.core.ControllerCallBack;
import com.zhc.tcp.core.ControllerCode;
import com.zhc.tcp.protocol.json.test;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 每个客户端
 * */

@Slf4j
@Component
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<ProtocolMsg>{

    @Autowired
    private BaseController baseController;


    /**
     * 这是接收到消息的监听
     * */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ProtocolMsg data) throws Exception {
        log.info("接收到"+channelHandlerContext.channel().remoteAddress()+"的消息:"+data.getProtocolId());

        if(baseController.methodMap.containsKey(ProtocolMsgId.CMD_LOGIN_REQ)){
            //既然存在命令那就设置一个回调  本不想写 但是奈何 写完后发现在控制器里没办法发送消息 没办法 多加个监听器
            //切记 要先设置完监听器 再去执行方法 不然执行方法的控制器中是没有监听器的 回调就会报错了
            //wc 这岂不是有几个命令就要有多少个回调情况 不行 还是将通道 发到控制器吧 或者直接设置进入吧
            //不过这个控制器就不删除了 留着多少是个玩意

            baseController.setOnControllerCallBack(new ControllerCallBack() {
                @Override
                public void callBack(int cmd, ControllerCode errCode) {
                    log.info("你好世界，我是控制器的回调");
                }
            });

            baseController.setSelfClient(channelHandlerContext.channel());

            Method method = baseController.methodMap.get(data.getMsgId());
            method.invoke(baseController,data);
        }else{
            log.info("客户端发送了一个未知命令");
        }
    }

    /**
     * 这是错误监听
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 这是通道准备就绪监听
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道建立完毕");
    }


    /**
     * 这是通道未准备就绪监听
     * */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("退出客户端");
        ctx.close();
    }

}
