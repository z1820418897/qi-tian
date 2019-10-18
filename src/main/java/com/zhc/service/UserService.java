package com.zhc.service;

import com.zhc.model.User;

import java.util.List;

public interface UserService {
    void Test();
    User findUserByUserName(String userName);
    List<User> findFriendsByUserId(long from);
}
