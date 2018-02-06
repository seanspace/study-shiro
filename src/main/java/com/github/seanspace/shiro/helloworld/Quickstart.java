package com.github.seanspace.shiro.helloworld;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:helloworld/shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //获取当前的subject
        Subject currentUser = SecurityUtils.getSubject();

        //测试使用session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        //测试当前的用户是否已经被认证。（是否已经登录）
        if (!currentUser.isAuthenticated()) {
            //把密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                //执行登录。
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                //异常：没有指定的账户
                log.info("There is no user with username of " + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) {
                //异常：密码不对
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                return;
            } catch (LockedAccountException lae) {
                //异常：账户被锁
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                return;
            } catch (AuthenticationException ae) {
                //其他异常
                return;
            }
        }

        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //判断是否有角色
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //判断是否有权限（用户是否具备某一个行为）
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //登出
        currentUser.logout();

        System.exit(0);
    }
}
