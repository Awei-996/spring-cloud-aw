package online.k12code.core.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import online.k12code.core.enums.GlobalErrorCodeCodeConstants;
import online.k12code.core.exception.ErrorCode;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author msi
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private T data;


    public static <T> Result<T> success() {
        return new Result<T>()
                .setCode(GlobalErrorCodeCodeConstants.SUCCESS.getCode())
                .setMsg(GlobalErrorCodeCodeConstants.SUCCESS.getMsg());
    }


    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(GlobalErrorCodeCodeConstants.SUCCESS.getCode())
                .setMsg(GlobalErrorCodeCodeConstants.SUCCESS.getMsg())
                .setData(data);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<T>()
                .setCode(GlobalErrorCodeCodeConstants.SUCCESS.getCode())
                .setMsg(msg)
                .setData(data);
    }

    public static <T> Result<T> error() {
        return new Result<T>()
                .setCode(GlobalErrorCodeCodeConstants.FAIL.getCode())
                .setMsg(GlobalErrorCodeCodeConstants.FORBIDDEN.getMsg());
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<T>()
                .setCode(errorCode.getCode())
                .setMsg(errorCode.getMsg());
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>()
                .setCode(GlobalErrorCodeCodeConstants.FAIL.getCode())
                .setMsg(msg);
    }

    public static <T> Result<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        return new Result<T>()
                .setCode(code)
                .setMsg(message);
    }
}
