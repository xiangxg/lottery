package cn.hx.bat.sso.exception;

/**
 * 
 * Title: CustomException
 * Description: 系统自定义的异常类型，实际开发中可能要定义多种异常类型
 * @version 1.0
 */
public class CustomException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1039481103123448087L;

    // 异常信息
    private String message;
    //错误码
    private Integer code;
    
    private Object data;

    public CustomException(Integer code,String message) {
        super(message);
        this.message = message;
        this.code = code;

    }
    public CustomException(Integer code,String message,Object data) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = data;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
