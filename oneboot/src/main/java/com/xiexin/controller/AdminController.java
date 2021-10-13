package com.xiexin.controller;

import com.xiexin.bean.Admin;
import com.xiexin.bean.AdminExample;
import com.xiexin.respcode.Result;
import com.xiexin.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/admin")
public class AdminController {
@Autowired
private AdminService adminService;
    @RequestMapping("/loginByShiro")
    public Result loginByShiro(@RequestBody Admin admin){
        //登录交给shiro的securityManger 妈妈管理
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getAdminAccount(), admin.getAdminPwd());
        try {
            subject.login(token);  //ok
            return new Result(0,"登录成功");
        } catch (AuthenticationException e) {  //出错了
            e.printStackTrace();
            return new Result(4001,"账户名或密码不对");
        }
    }
    //登录
    @RequestMapping("/login")
    public Map login(Admin admin, HttpSession session) {
        Map codeMap = new HashMap();
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminNameEqualTo(admin.getAdminName());
        criteria.andAdminPwdEqualTo(admin.getAdminPwd());
        List<Admin> admins = adminService.selectByExample(example);
        if (admins != null && admins.size() > 0) {
            Admin admin1 = admins.get(0);
            //把这个账号信息放到session中
            session.setAttribute("admin1", admin1);
            codeMap.put("code", 0);
            codeMap.put("msg", "登录成功");
            codeMap.put("adminName", admin1.getAdminName());
            return codeMap;
        } else {
            codeMap.put("code", 4001);
            codeMap.put("msg", "账号或者密码错误");
            return codeMap;
        }
    }
    //注册
    @RequestMapping("/insert")
    public Map insert(Admin admin) { //  对象传参, 规则: 前端属性要和后台的属性一致!!!
        Map map = new HashMap();
        int i = adminService.insertSelective(admin);
        if (i > 0) {
            map.put("code", 200);
            map.put("msg", "添加成功");
            return map;
        } else {
            map.put("code", 400);
            map.put("msg", "添加失败,检查网络再来一次");
            return map;
        }
    }


    //注册  /admin/reginByShiro
    @RequestMapping("/reginByShiro")
    public Result reginByShiro(@RequestBody Admin admin) { // orders 对象传参, 规则: 前端属性要和后台的属性一致!!!
        String salt = getRandomCharacterAndNumber(6);
        Md5Hash pwd = new Md5Hash(admin.getAdminPwd(), salt, 1024);
        admin.setAdminName(admin.getAdminName());
        admin.setAdminPwd(pwd.toString());
        admin.setAdminPhone(admin.getAdminPhone());
        admin.setAdminAccount(admin.getAdminAccount());
        admin.setSalt(salt);
        admin.setTiwen(0.0D);
        admin.setStatus(0);
        int i = adminService.insertSelective(admin);

        return new Result();
    }
    //随机盐
    public static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}