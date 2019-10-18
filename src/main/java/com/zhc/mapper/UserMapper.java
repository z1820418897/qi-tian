package com.zhc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhc.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT u.user_id,u.nick_name from tb_friends f,tb_user u where u.user_id=f.friend_id and f.user_id=#{from}")
    List<User> selectFriends(long from);
}
