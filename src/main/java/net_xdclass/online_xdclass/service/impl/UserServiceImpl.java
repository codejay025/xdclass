package net_xdclass.online_xdclass.service.impl;

import net_xdclass.online_xdclass.model.entity.User;
import net_xdclass.online_xdclass.mapper.UserMapper;
import net_xdclass.online_xdclass.service.UserService;
import net_xdclass.online_xdclass.utils.CommonUtils;
import net_xdclass.online_xdclass.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Map<String, String> userInfo) {
        /**
         * 将前端传入的用户信息进行JavaBean封装
         */
        User user = parseToUser(userInfo);
        if (user != null) {
            return  userMapper.save(user);
        } else {
            return -1;
        }
    }

    /**
     * 用户的登录的校验
     * @param phone
     * @param pwd
     * @return
     */
    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        /**
         * 对Controller层传入的数据进行封装，密码进行MD5加密！
         * */
        User user = userMapper.findByPhoneAndPwd(phone, CommonUtils.MD5(pwd));
        /*
        * 查出来的结果使用User进行封装，判断结果是否为空
        * 为空的话，返回null
        * 不为空话，生成token进行返回
        * */
        if (user == null) {
            return null;
        } else {
            String token = JWTUtils.geneJsonWebToken(user);
            return token;
        }
    }

    /**
     *  通过用户的ID查询用户的具体信息
     * @param userId
     * @return
     */
    public User findByUserId(Integer userId) {
        User user = userMapper.findByUserId(userId);

        // 查询出来的结果不能有密码，所以将密码 置空
        // user.setPwd("");

        return user;
    }

    /**
     * 当用户注册的时候这些值是必填的
     * @param userInfo
     * @return
     */
    private User parseToUser(Map<String, String> userInfo) {
        // 判断用户是否填写以下信息，填写后，将填写后的信息进行封装到JavaBean中
        if (userInfo.containsKey("phone") && userInfo.containsKey("pwd") && userInfo.containsKey("name")) {
            User user = new User();
            /* 获取键对应的值 */
            user.setName(userInfo.get("name"));
            user.setPhone(userInfo.get("phone"));
            user.setHeadImg(getRandomImg());
            String pwd = userInfo.get("pwd");

            user.setPwd(CommonUtils.MD5(pwd));
            user.setCreateTime(new Date());
            return user;
        } else {
            return null;
        }
    }

    /**
     *  随机图片
     */
    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    /**
     * 获取随机图片
     */

    private String getRandomImg(){
        int size = headImg.length;
        Random random = new Random();
        /* 获取size范围中的数 */
        int index = random.nextInt(size);
        // 获取动态随机数
        return headImg[index];
    }
}
