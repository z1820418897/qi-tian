package com.zhc.tcp.codes;

import com.zhc.common.ProtocolMsg;
import com.zhc.tcp.tools.MsgTool;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义协议的解码器
 * */
public class ProtocolDecode extends ByteToMessageDecoder {

    private final static int HEAD_LENGTH = MsgTool.getHeadLengthByClassName();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("接收到新的消息");

        //可读的数据长度如果没有包头长 那直接就返回
        if(byteBuf.readableBytes()<HEAD_LENGTH) return;
        //标记一下读的位置
        byteBuf.markReaderIndex();

        //开始读取数据
        //先读取包头
        ProtocolMsg jsonMsg = MsgTool.buildMsgByHead(byteBuf);

        //要是读取的的数据包长度是0 那就只有包头 不用解析包体了
        if(jsonMsg.getLength()==0){
            return;
        }
        //查看可读的包体长度要是小于我们发送过来的长度，配合markReaderIndex重置到读的位置
        if(byteBuf.readableBytes()<jsonMsg.getLength()) {
            byteBuf.resetReaderIndex();
            return;
        }
        //都没有问题了 再把缓冲区数据读到字节数组里面
        byte[] body=new byte[jsonMsg.getLength()];

        //根据自己的逻辑 由于协议定义的问题 这里把包体发出去byte数组 将包头的内容也发进去

        //或者你仍旧可以在这里编写业务 或者回调出去

        //这样拿到的包体就是byte数组   那我们协议如果是json可以将byte数组转化成json，是protobuf可以将数组转化成protobuf
        byteBuf.readBytes(body);

        jsonMsg.setBody(body);
        list.add(jsonMsg);

    }
}
