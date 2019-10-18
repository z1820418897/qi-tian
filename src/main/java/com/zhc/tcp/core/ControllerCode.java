package com.zhc.tcp.core;

public enum ControllerCode {
    SUCCESS(0),

    LOGIN_FAILE(101);//账号密码错误


    final int value;
    ControllerCode(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
