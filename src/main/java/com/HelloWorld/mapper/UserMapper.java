package com.HelloWorld.mapper;

import com.HelloWorld.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id, password, last_name, first_name FROM users WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "password", column = "password"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "firstName", column = "first_name")
    })
    User findById(Integer userId);

}
