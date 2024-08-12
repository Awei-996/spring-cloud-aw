package online.k12code.core.exception;

import lombok.Data;

/**
 * 错误码对象
 * 全局错误码，占用[0-999] {@link GlobalErrorCodeConstans}
 * 业务异常错误码，1000-+∞ {@link ServiceErrorCodeConstans}
 * <p>
 * TODO 错误码设计成对象的原因，为未来的i18国际化做准备
 *
 * @author msi
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误信息
     */
    private final String msg;

    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
