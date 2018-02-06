package com.github.seanspace.shiro.factory;

import java.util.LinkedHashMap;

/**
 * 实例工厂方法,实现权限栈的配置
 * @author xiaobin
 * @date 2017/11/21
 */
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/login.jsp", "anon");
        map.put("/**", "authc");
        return map;

    }
}
