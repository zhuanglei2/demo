package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/1 16:24
 */
@Component
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from sec_user where username like '%${username}%' or email like '%${email}%' or phone like '%${phone}%'")
    User findByUsernameOrEmailOrPhone(@Param("username") String username, @Param("email")String email, @Param("phone")String phone);

    @Select("<script> select * from sec_user where username in " +
            "<foreach item='item' index='index' collection='usernameList' open='(' separator=',' close=')'> #{item} </foreach> "+
            "</script>")
    List<User> findByUsernameIn(@Param("usernameList") List<String> usernameList);
}
