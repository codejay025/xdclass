package net_xdclass.online_xdclass.service;

import net_xdclass.online_xdclass.model.entity.User;

import java.util.Map;

public interface UserService {



    /**
     * 新增用户
     * @param map
     * @return
     */
    int save(Map<String,String> map);

    /**
     * 用户的登录
     * @param phone
     * @param pwd
     * @return
     */
    String findByPhoneAndPwd(String phone, String pwd);


    User findByUserId(Integer userId);
}
