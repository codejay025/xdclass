package net_xdclass.online_xdclass.controller;

import net_xdclass.online_xdclass.mapper.UserMapper;
import net_xdclass.online_xdclass.model.entity.User;
import net_xdclass.online_xdclass.model.request.LoginRequest;
import net_xdclass.online_xdclass.service.UserService;
import net_xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pri/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @RequestBody
     *     是封装前端返回的数据的，比如前端的JSON数据等，键值对的形式
     *
     *     将前端传入的数据以Map集合的形式存储
     * */
    @PostMapping("/register")
    public JsonData register(@RequestBody Map<String,String> userInfo){
        int rows = userService.save(userInfo);
        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildError("注册失败，请重试！");
    }

    /**
     * 登录校验的方法,从前端请求中获取电话号码和密码，传入后端验证，生成token
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    /**
     * 将前端传入的信息封装到loginRequest里面
     * 也就是说，通过登录成功，服务器端向前端返回的是一个token
     * */
    public JsonData login(@RequestBody LoginRequest loginRequest) {
        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(), loginRequest.getPwd());
        return token == null ? JsonData.buildError("登录失败，账号密码错误！") : JsonData.buildSuccess(token);
    }

    /**
     * 根据用户id查询用户信息,因为要获取拦截器中request域中的id，所以需要request
     */
    @GetMapping("/find_by_id")
    public JsonData findUserByToken(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");

        // 判断userId 是否为空
        if (userId == null) {
            return JsonData.buildError("查询失败！");
        }

        // 要不然就是查询成功 ，返回User对象
        User user = userService.findByUserId(userId);
        return JsonData.buildSuccess(user);
    }
}
