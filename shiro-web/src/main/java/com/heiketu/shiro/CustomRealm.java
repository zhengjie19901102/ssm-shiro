package com.heiketu.shiro;

import com.heiketu.dao.PermissionMapper;
import com.heiketu.dao.RoleMapper;
import com.heiketu.dao.UserMapper;
import com.heiketu.pojo.Permissions;
import com.heiketu.pojo.Role;
import com.heiketu.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {


    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;


    /**
     * 权限[角色|权限]
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UsernamePasswordToken ot = (UsernamePasswordToken)principals.getPrimaryPrincipal();
        System.out.println("---- 从数据库中获取 ----");
        //查询角色
        Set<String> roles = roleMapper.selectByUsername(ot.getUsername());
        //查询权限
        Set<String> permissions = permissionMapper.selectByRoleName(roles);
        Example example = new Example(Permissions.class);
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.setStringPermissions(permissions);
        sa.setRoles(roles);
        return sa;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",upToken.getUsername());
        List<User> users = userMapper.selectByExample(example);
        SimpleAuthenticationInfo sa = new SimpleAuthenticationInfo(token,users.get(0).getPassword()
                ,this.getName());
        return sa;
    }
}
