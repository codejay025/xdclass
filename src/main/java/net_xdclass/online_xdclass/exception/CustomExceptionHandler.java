package net_xdclass.online_xdclass.exception;

import net_xdclass.online_xdclass.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 通过日志打印异常信息
     */
    private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
    /**
     * 定义一个异常处理方法,以JsonData的形式返回给前端
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){

        /*
            通过判断异常的类型，进行处理.如果是自定义异常类中的异常，那就传入JsonData数据
         */
        if (e instanceof XDException) {
            logger.error("[系统异常]{}", e);
            XDException xdException = (XDException) e;
            return JsonData.buildError(xdException.getCode(), xdException.getMsg());
        } else {
            // 如果不是自定义异常的话，返回
            return JsonData.buildError("全局异常，未知错误！");
        }
    }
}
