# Shiro


### 1. 首次使用
```
//获取Subject
Subject currentUser = SecurityUtils.getSubject();

//获取Session
Session session = currentUser.getSession();

//判断是否已经登录
currentUser.isAuthenticated()

//进行登录认证
UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
currentUser.login(token);

//如果登录成功能获取信息
currentUser.getPrincipal()

//判断是否有角色
if (currentUser.hasRole("schwartz"))

//判断是否有某个类型（winnebago）的id（eagle5）的行为（drive）
if (currentUser.isPermitted("winnebago:drive:eagle5"))

```
