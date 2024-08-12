package online.k12code.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器异常 Exception
 *
 * @author msi
 * @EqualsAndHashCode(callSuper = true) 用于生成equals和hashcode方法，参数callSuper = true 表示生成的方法会调用父类的equals和hashcode方法
 *      一致性：如果类有父类，并且父类中需要比较的字段，设置 callSuper 是一种所有字段都被正确的比较的好方法
 *类 final 关键字，表示这个类不能被继承，JVM可以针对final类进行优化，因为他知道这些类不会有子类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ServerException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;


    public ServerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public ServerException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServerException setMessage(String message) {
        this.message = message;
        return this;
    }

}
