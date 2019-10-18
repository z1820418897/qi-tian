package com.zhc.tcp.codes;

import com.zhc.common.ProtocolMsg;
import com.zhc.tcp.tools.MsgTool;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义协议的编码器
 * */
public class ProtocolEncode extends MessageToByteEncoder<ProtocolMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolMsg data, ByteBuf byteBuf) throws Exception {
        //排好队 一个一个来  要按顺序把数据放进缓冲区
        //wc按理说一个编码器应该很多代码才对 这就这一行 搞得怀疑自己
        MsgTool.writerMsgByBuf(byteBuf,data);

    }
}
