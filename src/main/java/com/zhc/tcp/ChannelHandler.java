package com.zhc.tcp;

import com.zhc.tcp.codes.ProtocolDecode;
import com.zhc.tcp.codes.ProtocolEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 监听客户端
 * */

@Slf4j
@Component
public class ChannelHandler extends ChannelInitializer<SocketChannel> {

    @Resource
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("有新的客户端连接"+socketChannel.remoteAddress());

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));

        //方便起见我们用json  不用在写协议生成了 接收用JsonMsg
        pipeline.addLast(new ProtocolDecode());
        pipeline.addLast(new ProtocolEncode());

        pipeline.addLast(serverHandler);
    }


}
