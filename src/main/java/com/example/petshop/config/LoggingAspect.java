package com.example.petshop.config; // 声明切面配置所在包。 
import org.aspectj.lang.ProceedingJoinPoint; // 引入环绕通知连接点对象。 
import org.aspectj.lang.annotation.Around; // 引入环绕通知注解。 
import org.aspectj.lang.annotation.Aspect; // 引入切面注解。 
import org.slf4j.Logger; // 引入日志接口。 
import org.slf4j.LoggerFactory; // 引入日志工厂。 
import org.springframework.stereotype.Component; // 引入组件注解。 
@Aspect // 声明该类是 Spring AOP 切面。 
@Component // 声明该切面由 Spring IoC 容器管理。 
public class LoggingAspect { // 定义日志和性能监控切面。 
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class); // 创建日志对象。 
    @Around("execution(* com.example.petshop.service..*(..))") // 对所有服务层方法织入环绕通知。 
    public Object logServiceCall(ProceedingJoinPoint joinPoint) throws Throwable { // 定义服务调用日志通知。 
        long start = System.currentTimeMillis(); // 记录方法开始时间。 
        try { // 执行业务方法并捕获结果。 
            Object result = joinPoint.proceed(); // 放行业务方法执行。 
            log.info("AOP日志：{} 执行成功，耗时 {} ms", joinPoint.getSignature().toShortString(), System.currentTimeMillis() - start); // 输出成功日志。 
            return result; // 返回业务方法结果。 
        } catch (Throwable throwable) { // 捕获业务方法异常。 
            log.warn("AOP日志：{} 执行失败，原因 {}", joinPoint.getSignature().toShortString(), throwable.getMessage()); // 输出异常日志。 
            throw throwable; // 继续抛出原始异常。 
        } // 结束异常处理。 
    } // 结束环绕通知。 
} // 结束日志切面。 
