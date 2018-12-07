package com.heiketu.dao;

import com.heiketu.pojo.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

public interface RoleMapper extends Mapper<Role> {

    Set<String> selectByUsername(@Param("username") String username);

}
