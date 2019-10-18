package com.zhc.tcp.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhc.common.ProtocolMsg;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class MsgTool {

    //通过反射获取类的包头的长度
    public static Integer getHeadLengthByClassName() {
        Integer headLength=0;
        try {
            Class jsonMsg = Class.forName("com.zhc.common.ProtocolMsg");
            Field[] fields = jsonMsg.getDeclaredFields();
            Field.setAccessible(fields,true);
            for(Field field:fields){
                String type=field.getType().toString();
                if(type.equals("byte")||type.equals("boolean")){
                    headLength++;
                }else if(type.equals("short")||type.equals("char")){
                    headLength+=2;
                }else if(type.equals("int")||type.equals("float")){
                    headLength+=4;
                }else if(type.equals("long")||type.equals("double")){
                    headLength+=8;
                }
            }
        }catch (Exception e){
            log.error("反射数据头长度出现异常\n"+e);
        }
        return headLength;
    }

    //将缓冲区固定的数据转化成对象头
    //这里有个问题遗留 因为缓冲区要按顺序放入数据  这里用反射获取属性的排序 我不知道会不会特殊性况下错乱，目前正常，但万一错乱就很尴尬  有空可以用字段名反射或者责任链模式重写一下
    public static ProtocolMsg buildMsgByHead(ByteBuf byteBuf){
        ProtocolMsg jsonMsg = new ProtocolMsg();
        try {
            Class jsonMsgClass = jsonMsg.getClass();
            Field[] fields = jsonMsgClass.getDeclaredFields();
            Field.setAccessible(fields,true);
            for(Field field:fields){
                String type=field.getType().toString();
                switch (type){
                    case "byte": field.set(jsonMsg,byteBuf.readByte()); break;
                    case "boolean":field.set(jsonMsg,byteBuf.readBoolean());break;
                    case "short":field.set(jsonMsg,byteBuf.readShort());break;
                    case "char":field.set(jsonMsg,byteBuf.readChar());break;
                    case "int":field.set(jsonMsg,byteBuf.readInt());break;
                    case "float":field.set(jsonMsg,byteBuf.readFloat());break;
                    case "long":field.set(jsonMsg,byteBuf.readLong());break;
                    case "double":field.set(jsonMsg,byteBuf.readDouble());break;
                }
            }
        }catch (Exception e){
            log.error("反射数据头出现异常\n"+e);
        }
        return jsonMsg;

    }


    //将所有数据转化成
    public static void writerMsgByBuf(ByteBuf byteBuf,ProtocolMsg msg){
        try {
            Class msgClass = msg.getClass();
            Field[] fields = msgClass.getDeclaredFields();
            Field.setAccessible(fields,true);
            for(Field field:fields){
                String type=field.getType().toString();
                switch (type){
                    case "byte":byteBuf.writeByte(field.getByte(msg));break;
                    case "boolean":byteBuf.writeBoolean(field.getBoolean(msg)) ;break;
                    case "short":byteBuf.writeShort(field.getShort(msg)) ;break;
                    case "char":byteBuf.writeChar(field.getChar(msg)) ;break;
                    case "int":byteBuf.writeInt(field.getInt(msg)) ;break;
                    case "float":byteBuf.writeFloat(field.getFloat(msg)) ;break;
                    case "long":byteBuf.writeLong(field.getLong(msg));break;
                    case "double": byteBuf.writeDouble(field.getDouble(msg));break;
                    case "class [B":byteBuf.writeBytes((byte[])field.get(msg));break;
                }
            }
        }catch (Exception e){
            log.error("反射所有数据出现异常\n"+e);
        }
    }

    //数组转化成字符串
    public static JSONObject buildBody(byte[] b){
        String ss = new String(b);

        char[] chars = ss.toCharArray();

        StringBuffer str = new StringBuffer();
        for (char c : chars) {

            Character ch = c;

            if (0 == ch.hashCode()) {
                break;
            } else {
                str.append(c);
            }
        }

        return JSON.parseObject(str.toString());
    }

    public static void main(String arg[]) throws IllegalAccessException {
        ProtocolMsg msg=new ProtocolMsg();
        msg.setMsgId(100);
        msg.setLength(200);
        byte[] bytes = {1,2,3,4,5};
        msg.setBody(bytes);

        Class msgClass = msg.getClass();
        Field[] fields = msgClass.getDeclaredFields();
        Field.setAccessible(fields,true);
        for(Field field:fields){
            String type=field.getType().toString();
            System.out.println(type);
            switch (type){
                case "int":System.out.println(""+field.getInt(msg));break;
                case "class [B": System.out.println(((byte[])field.get(msg))[4]);break;
            }


        }
    }
}
