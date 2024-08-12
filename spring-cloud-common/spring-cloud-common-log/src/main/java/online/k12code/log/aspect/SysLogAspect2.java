package online.k12code.log.aspect;

import lombok.extern.slf4j.Slf4j;
import online.k12code.log.annotation.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 *  另一种写法，也是用aop
 *  使用el表达式的方式注入切点 execution(* online.k12code.*.controller.*.*(..))
*                                    * 表示返回的任意类型
 *                                   online.k12code.*.controller.* 表示扫描这个所有的类
 *                                   .*(..) 任意方法不管参数多少
 * 环绕通知的时候
 * Object proceed = joinPoint.proceed(); 这行代码是真正执行你自己的方法逻辑，在这之前就是前置通知，在之后就是后置通知
 * @author msi
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect2 {
    @Pointcut("execution(* online.k12code.*.controller.*.*(..)) ")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        // 获取注解
        MethodSignature signature1 = (MethodSignature) joinPoint.getSignature();
        Method method1 = signature1.getMethod();
        SysLog annotation = method1.getAnnotation(SysLog.class);
        // 获取方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getMethod().toString();
        // 如果添加了注解就获取注解中的描述信息 没有就获取方法名字
        String methodName = annotation == null ? method : annotation.value();

        log.info("环绕通知开始: {}", methodName);

        try {
            Object proceed = joinPoint.proceed();

            Instant end = Instant.now();

            Duration between = Duration.between(start, end);

            String duration = between.toString();
            long millis = between.toMillis();

            log.info("环绕通知结束: {}, 执行耗时: {}ms({})", methodName, millis, duration);

            return proceed;
        }
        catch (Exception e) {
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            String duration = between.toString();
            long millis = between.toMillis();

            log.error("异常通知: {} 执行耗时: {}ms({})", methodName, millis, duration, e);
            throw e;
        }
    }
}
