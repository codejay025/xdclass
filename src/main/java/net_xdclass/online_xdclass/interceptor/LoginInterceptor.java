package net_xdclass.online_xdclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import net_xdclass.online_xdclass.utils.JWTUtils;
import net_xdclass.online_xdclass.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 登录成功
         */
        try {

            // 先在Header里面是否有token
            String accessToken = request.getHeader("token");

            // 如果accessToken为空的话，那就从请求参数里面获取
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }

            // 如果不为空的话，就校验,token是字符串类型
            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtils.checkJWT(accessToken);

                // 判断claims是否为空，为空的话，可能是token令牌过期，解密失败，或者是被篡改了
                if (claims == null) {

                    // 登录过期，重新登录
                    sendMessage(response, JsonData.buildError("登录过期，请重新登录！"));
                    return false;
                }

                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                //Controller怎么获取到呢？ 通过request域  将解密过的数据放到request域中
                request.setAttribute("user_id", id);
                request.setAttribute("name", name);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 登录失败返回false,同时给用户登录信息，accessToken是空的
         * token是登录过期的
         */
        sendMessage(response,JsonData.buildError("登录过期，请重新登录！"));
        return false;
    }

    /**
     * 相应Json数据给前端，数据流转换成json格式
     * 登录失败返回给前端的错误信息，将object（JsonData）序列化，然后返回回去
     *  将信息发送给Controller，通过流的形式写出去
     *  PrintWriter 输出流
     * @param response
     * @param object
     */
    public static void sendMessage(HttpServletResponse response,Object object){

        try {
            // 创建ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 设置相应类型
            response.setContentType("application/json;charset=utf-8");

            // 获取相应流对象
            PrintWriter writer = response.getWriter();

            // 使用响应流对象往外写文件,objectMapper.writeValueAsString(object)这个就是转换为json文件
            String json = objectMapper.writeValueAsString(object);
            writer.print(json);

            // 关闭资源
            writer.close();

            // 刷新相应流
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
