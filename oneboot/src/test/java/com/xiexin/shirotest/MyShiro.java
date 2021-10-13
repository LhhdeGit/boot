package com.xiexin.shirotest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;

/**
 * shiro 的加密和认证测试
 */
public class MyShiro {
   // shiro有.对明文密码123456有加密的功能，ilweb的密码更加安全
//md5加密 简单 但是不可逆 但是可以根据 加密后的密码  进行反推
    //更加安全！！ 就需要加盐  salt
    @Test
   public void  testCmd5(){
        Md5Hash md5Hash = new Md5Hash("123456");
        System.out.println("md5Hash = " + md5Hash);

        //给密码加盐
        Md5Hash md5Hash1 = new Md5Hash("123456", "xieshadouxing");
        System.out.println("md5Hash1 = " + md5Hash1);

        //给加盐后的密码 进行散列处理
        Md5Hash md5Hash2 = new Md5Hash("123456", "xieshadouxing",1024); //社工 大数据
        System.out.println("md5Hash2 = " + md5Hash2);
    }


}
