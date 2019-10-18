package com.zhc.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhc.annotation.LogApi;
import com.zhc.mapper.UserMapper;
import com.zhc.model.User;
import com.zhc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@LogApi("你好世界")
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void Test() {
        List<User> users = userMapper.selectList(null);
        log.info(users.get(0).toString());
    }

    @Override
    public User findUserByUserName(String userName) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);

        return user;
    }

    @Override
    public List<User> findFriendsByUserId(long from) {

        List<User> friends=userMapper.selectFriends(from);

        return friends;
    }
}
