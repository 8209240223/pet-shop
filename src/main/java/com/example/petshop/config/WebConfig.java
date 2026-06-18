package com.example.petshop.config; // 声明 Web 配置所在包。 
import com.example.petshop.legacy.LegacyMvcServlet; // 引入传统 MVC Servlet。 
import org.springframework.boot.web.servlet.ServletRegistrationBean; // 引入 Servlet 注册 Bean。 
import org.springframework.context.annotation.Bean; // 引入 Bean 声明注解。 
import org.springframework.context.annotation.Configuration; // 引入配置类注解。 
import org.springframework.web.servlet.config.annotation.CorsRegistry; // 引入 CORS 注册器。 
import org.springframework.web.servlet.config.annotation.InterceptorRegistry; // 引入拦截器注册器。 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // 引入 MVC 配置接口。 
@Configuration // 声明该类是 Spring JavaConfig 配置类。 
public class WebConfig implements WebMvcConfigurer { // 定义 Web MVC 配置类。 
    private final AuthInterceptor authInterceptor; // 保存认证拦截器依赖。 
    public WebConfig(AuthInterceptor authInterceptor) { this.authInterceptor = authInterceptor; } // 通过构造器注入认证拦截器。 
    @Override // 声明重写跨域配置方法。 
    public void addCorsMappings(CorsRegistry registry) { // 配置前后端分离常见的跨域规则。 
        registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*"); // 允许前端通过 AJAX 访问 REST API。 
    } // 结束跨域配置方法。 
    @Override // 声明重写拦截器配置方法。 
    public void addInterceptors(InterceptorRegistry registry) { // 注册 Spring MVC 拦截器。 
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/admin/**"); // 只保护管理员 API。 
    } // 结束拦截器配置方法。 
    @Bean // 声明一个传统 Servlet 注册 Bean。 
    public ServletRegistrationBean<LegacyMvcServlet> legacyMvcServlet() { // 注册用于学习 Servlet/MVC/Struts 思想的 Servlet。 
        return new ServletRegistrationBean<>(new LegacyMvcServlet(), "/legacy/servlet-mvc"); // 将传统前端控制器映射到指定路径。 
    } // 结束 Servlet 注册方法。 
} // 结束 Web 配置类。 
