package com.zhc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    @TableField("user_name")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("nick_name")
    private String nickName;
}
