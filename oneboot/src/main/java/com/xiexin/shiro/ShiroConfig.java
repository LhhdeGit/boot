package com.xiexin.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * shiro 的web配置
 * 目的：因为shiro 可以和 很多 项目适配 那么我们是web项目 就需要配置成web的securityManger
 * 又因为是web项目 所以 需要使用 过滤器 来配置 需要拦截 和非拦截请求
 *
 */
@Configuration  // 这是一个配置类的注解 该注解是 配置的意思 顶替的是xml中的配置
                //优先其他注解优先执行。
public class ShiroConfig {
    //1.shiroconfig 需要指明realm是谁 并且把这个realm创建出来 这个创建指的是 优先于 其他的controller service等
    //对象 优先创建
    @Bean
    public Realm getMybatisRealm(){
        MybatisRealm realm = new MybatisRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1024);
        realm.setCredentialsMatcher(matcher); //注入匹配 注入加盐加密的匹配
        return realm;
    }


    //2.指派securityManger 因为我们是web项目 所以应该是websecurityManger
    @Bean
    public DefaultWebSecurityManager getSecurityManager(Realm realm){
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(realm);
        return sm;
    }
    //以上 仙女们和妈妈桑 就勾搭在一起了

    //3.剩男 subject
    @Bean
    public  ShiroFilterFactoryBean getFilter(DefaultWebSecurityManager sm){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();;
        shiroFilterFactoryBean.setSecurityManager(sm);

        //使用过滤器
        Map map = new LinkedHashMap<>(); //这个map是有顺序的
        //不拦截页面！！！
        map.put("/page/login","anon");  //anno匿名的
        map.put("/admin/loginByShiro","anon");  //登录方法不拦截
        map.put("/admin/addByShiro","anon");  //注册不拦截
        map.put("/*/**","authc");//authc需要登录

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map); //把拦截的顺序 放入到linkedmap中！！！
        return  shiroFilterFactoryBean;
    }
}
