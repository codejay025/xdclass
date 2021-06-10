package net_xdclass.online_xdclass.exception;

/**
 * 自定义异常类
 */
public class XDException extends RuntimeException{

    // 异常状态码
    private Integer code;

    // 返回错误信息
    private String msg;

    public XDException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "XDException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
