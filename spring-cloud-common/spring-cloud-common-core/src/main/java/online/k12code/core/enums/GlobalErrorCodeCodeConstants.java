package online.k12code.core.enums;

import online.k12code.core.exception.ErrorCode;

/**
 * 全局错误枚举
 * 一般情况下，使用HTTP响应状态码 https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 *
 * @author msi
 */
public interface GlobalErrorCodeCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(0, "成功");
    ErrorCode FAIL = new ErrorCode(1, "失败");
    /**
     * 客户端错误码
     */
    ErrorCode BAD_REQUEST = new ErrorCode(400, "请求参数错误");
    ErrorCode FORBIDDEN = new ErrorCode(403, "没有该操作权限");
    ErrorCode NOT_FOUND = new ErrorCode(404, "请求未找到");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "请求方法不正确");
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "请求过于频繁，请稍后重试");
    /**
     * 服务端错误码
     */
    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "系统异常");
    /**
     * 自定义错误
     */
    ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "重复请求，请稍后重试");

}
