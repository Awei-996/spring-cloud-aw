package online.k12code.log.aspect;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import online.k12code.core.utils.IpUtils;
import online.k12code.core.utils.SecurityUtils;
import online.k12code.log.model.SysOperLog;
import online.k12code.log.service.AsyncLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import online.k12code.log.annotation.SysLog;

import java.util.Date;

/**
 * 切面日志
 *
 * @author msi
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    private final AsyncLogService asyncLogService;

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    public SysLogAspect(AsyncLogService asyncLogService) {
        this.asyncLogService = asyncLogService;
    }

    /**
     * 处理请求之前
     */
    @Before(value = "@annotation(sysLog)")
    public void before(JoinPoint joinPoint, SysLog sysLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(value = "@annotation(sysLog)", returning = "jsonResult")
    public void afterReturning(JoinPoint joinPoint, SysLog sysLog, Object jsonResult) {
        handleLog(joinPoint, sysLog, null, joinPoint);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "@annotation(sysLog)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, SysLog sysLog, Exception e) {
        handleLog(joinPoint, sysLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, SysLog sysLog, final Exception e, Object jsonResult) {
        try {
            SysOperLog sysOperLog = new SysOperLog();
            // 请求的地址
            sysOperLog.setOperIp(IpUtils.getIpAddr());
            // 请求uri
            sysOperLog.setOperUrl(IpUtils.getRequest().getRequestURI());
            // 用户名
           sysOperLog.setOperName(SecurityUtils.getUserName());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysOperLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            sysOperLog.setRequestMethod(IpUtils.getRequest().getMethod());
            // 设置消耗的时间
            sysOperLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            // 设置操作时间
            sysOperLog.setOperTime(new Date());
            // 设置请求参数
            sysOperLog.setOperParam(JSON.toJSONString(IpUtils.getRequest().getParameterMap()));
            // 设置相应参数
            sysOperLog.setJsonResult(JSON.toJSONString(jsonResult));
            // 设置状态
            if (e != null) {
                sysOperLog.setStatus(1);
                sysOperLog.setErrorMsg(e.getMessage());
            } else {
                sysOperLog.setStatus(0);
            }
            // 保存数据库
            asyncLogService.saveLog(sysOperLog);
        } catch (Exception exp) {
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }
}
