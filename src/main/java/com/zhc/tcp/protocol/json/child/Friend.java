package com.zhc.tcp.protocol.json.child;

public class Friend {
    public long user_id;
    public String nick_name;
    public byte state;

    public Friend(){}

    public Friend(long user_id,String nick_name,byte state){
        this.user_id=user_id;
        this.nick_name=nick_name;
        this.state=state;
    }


}
