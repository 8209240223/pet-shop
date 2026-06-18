package com.example.petshop.config; // 声明拦截器所在包。 
import com.example.petshop.service.AuthService; // 引入认证服务。 
import org.springframework.stereotype.Component; // 引入组件注解。 
import org.springframework.web.servlet.HandlerInterceptor; // 引入 Spring MVC 拦截器接口。 
import javax.servlet.http.HttpServletRequest; // 引入 Servlet 请求对象。 
import javax.servlet.http.HttpServletResponse; // 引入 Servlet 响应对象。 
@Component // 声明该拦截器由 Spring IoC 容器管理。 
public class AuthInterceptor implements HandlerInterceptor { // 定义管理员接口认证拦截器。 
    private final AuthService authService; // 保存认证服务依赖。 
    public AuthInterceptor(AuthService authService) { this.authService = authService; } // 通过构造器注入认证服务。 
    @Override // 声明重写请求预处理方法。 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { // 在 Controller 执行前检查权限。 
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) { return true; } // 放行跨域预检请求。 
        String header = request.getHeader("Authorization"); // 读取 Authorization 请求头。 
        String token = header == null ? null : header.replace("Bearer ", ""); // 从 Bearer 格式中取出令牌。 
        if (authService.verify(token)) { return true; } // 令牌有效时放行请求。 
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 令牌无效时返回 401 状态。 
        response.setContentType("application/json;charset=UTF-8"); // 设置 JSON 响应类型。 
        response.getWriter().write("{\"success\":false,\"message\":\"管理员接口需要 JWT Token\"}"); // 输出统一错误消息。 
        response.getWriter().flush(); // 确保响应内容立即写入。 
        return false; // 阻止请求继续执行。 
    } // 结束预处理方法。 
} // 结束认证拦截器。 
