package com.github.seanspace.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * AuthorizingRealm 继承它就可以实现认证和授权
 * @author xiaobin
 * @date 2017/11/21
 */
public class MyAuthorizingRealm extends AuthorizingRealm {

    /**
     * 授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1. 从principalCollection 获取登陆用户信息
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        //2. 利用登陆的用户的信息来用户当前用户的的角色或权限
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(primaryPrincipal)) {
            roles.add("admin");
        }
        //3. 创建SimpleAuthorizationInfo，并设置reles属性
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //4.
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
