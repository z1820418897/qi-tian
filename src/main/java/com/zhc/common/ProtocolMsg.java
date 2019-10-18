package com.zhc.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProtocolMsg {
    private short protocolId; // json或者protobuf协议版本 方便扩展 2byte    101 102--- 都是json  201 202--都是protobuf
    private int length; //注意 只给了包体的长度 当然你也可以给全部 反正包头长度都是提前协议好的 4byte
    private long from;  //发送方的id 8byte
    private long to; //  接收放的id 8byte
    private int msgId; // 消息id 或者叫命令 通常是一个用枚举定义好
    private byte[] body; //没什么可说的 消息体 上面的都是消息头
}


