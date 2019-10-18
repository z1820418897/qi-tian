package com.zhc.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 这是netty的启动类
 * */

@Slf4j
@Component
public class NettyServer {

    @Value("${port}")
    private int port;

    @Resource
    private ChannelHandler channelHandler;

    public void run(){
        EventLoopGroup bootstrapGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bootstrapGroup,workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG,1024);//连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);//不延迟 直接发送
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);//长连接
            bootstrap.childHandler(channelHandler);

            //绑定端口
            ChannelFuture f = bootstrap.bind(port).sync();
            log.info("监听端口："+port);
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bootstrapGroup.shutdownGracefully();
        }
    }
}
