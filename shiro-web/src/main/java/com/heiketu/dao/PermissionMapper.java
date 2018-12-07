package com.heiketu.dao;

import com.heiketu.pojo.Permissions;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface PermissionMapper extends Mapper<Permissions> {

    Set<String> selectByRoleName(@Param("roleName") Set<String> roleName);

}
