package com.github.seanspace.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 如果只实现登陆认证功能，直接继承AuthenticatingRealm
 */
public class MyAuthenticatingRealm extends AuthenticatingRealm {
    private static final transient Logger log = LoggerFactory.getLogger(MyAuthenticatingRealm.class);
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //实现
        log.info("-----Begin realm");
        //1. 把AuthenticationToken转为usernamepasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //2. 获取username
        String username = token.getUsername();
        //3. 调整数据的方法，从数据库中查询username对应的用户记录
        log.info("查询数据库");
        //4. 若用户不存在，则可以抛出unknownAccountException
        if (("unknown".equals(username))) {
            throw new UnknownAccountException("用户不存在");
        }
        //5.根据用户信息情况，决定是否抛出其他异常

        if ("monster".equals((username))) {
            throw new LockedAccountException("用户被锁定");
        }
        //6. 根据用户情况，来构建AuthenticationInfo对象

        //1).认证的实体信息，可以是username也可以是数据表对应的用户实体
        Object principal = username;
        //2）.密码.返回正确密码，密码比对有shiro完成，并且下次比对可能直接在缓存比较
        Object credentials = "123456";
        //3.). realmName :当前realm对象的name，调用父类的getName（）方法即可
        String realmName = getName();
        //SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        // 盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }
}
