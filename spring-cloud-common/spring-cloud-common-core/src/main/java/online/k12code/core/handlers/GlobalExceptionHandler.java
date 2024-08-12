package online.k12code.core.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.k12code.core.enums.GlobalErrorCodeCodeConstants;
import online.k12code.core.exception.ErrorCode;
import online.k12code.core.exception.ServerException;
import online.k12code.core.utils.Result;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

import static online.k12code.core.enums.GlobalErrorCodeCodeConstants.METHOD_NOT_ALLOWED;

/**
 * 全局异常处理
 *
 * @author msi
 * @RestControllerAdvice 他结合了@ControllerAdvice和@ResponseBody,意味着方法默认将返回值作为HTTP的响应体，不需要单独定义@ResponseBody注解
 * 主要功能：
 * 全局异常处理：使用@ExceptionHandler 注解来处理全局的异常，响应体会自动转换JSON或XML
 * 全局数据绑定：@InitBinder 注解来处理全局的数据绑定
 * 全局数据预处理： @ModelAttribute 注解来处理全局的数据预处理
 */
@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final String applicationName;

    /**
     * 自定义服务异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<?> handleServerException(ServerException e) {
        log.info(applicationName);
        log.error(e.getMessage(), e);
        return Result.error(new ErrorCode(e.getCode(), e.getMessage()));
    }

    /**
     * 404异常拦截
     *注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(GlobalErrorCodeCodeConstants.NOT_FOUND);
    }

    /**
     * 处理Spring MVC 请求参数缺失
     * 接口上设置了 @RequestParam(“xx”)参数，结果并未转递参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        log.error("[missingServletRequestParameterExceptionHandler]", exception);
        return Result.error(GlobalErrorCodeCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", exception.getParameterName()));
    }

    /**
     * 处理MVC　请求参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        log.error("[missingServletRequestParameterExceptionHandler]", exception);
        return Result.error(GlobalErrorCodeCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", exception.getMessage()));
    }


    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage(), e);
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.error(new ErrorCode(GlobalErrorCodeCodeConstants.BAD_REQUEST.getCode(), message));
    }
    /**
     * MVC 请求方法不正确
     * A接口的方法为GET，结果请求方法为POST方式，导致不匹配
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException exception){
        log.error("[httpRequestMethodNotSupportedExceptionHandler]", exception);
        return Result.error(METHOD_NOT_ALLOWED.getCode(),String.format("请求方法不正确:%s", exception.getMessage()));
    }

    /**
     * 最大全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error("系统异常，" + e.getMessage());
    }


}
