package com.xiexin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot项目是为了简化ssm项目而存在的
 * ssm项目配置比较繁琐，比如 配置Tomcat 需要 有很多个xml 去配置 第三方依赖
 * 而 springboot 简化成 内置的就内置 多个xml配置改为一个properties/ xml
 * 说白了 还是ssm框架  只不过写起来简单了
 */
@MapperScan("com.xiexin.dao")
@SpringBootApplication //springboot 应用注解 标记 本项目是springboot项目 必须有这个注解
public class SmartfoodApplication {
//main方法 项目一启动就会执行该方法
    public static void main(String[] args) {
        //静态调用springApplication应用 参数为 本项目的 启动类
        System.out.println("springboot 项目 启动了");
        SpringApplication.run(SmartfoodApplication.class, args);
    }

}
