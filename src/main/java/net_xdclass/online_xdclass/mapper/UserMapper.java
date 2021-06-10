package net_xdclass.online_xdclass.mapper;

import net_xdclass.online_xdclass.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 注册用户
     */
    int save(User user);

    /**
     *  通过前端传入的数据进行判断
     * */
    User findByPhone(@Param("phone") String phone);

    /**
     * 用户登录的校验
     * @param phone
     * @param pwd
     * @return
     */
    User findByPhoneAndPwd(@Param("phone") String phone,@Param("pwd") String pwd);

    User findByUserId(@Param("userId") Integer userId);
}
