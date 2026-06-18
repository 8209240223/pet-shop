package com.example.petshop; // 声明项目主类所在包。 
import org.mybatis.spring.annotation.MapperScan; // 引入 MyBatis Mapper 扫描注解。 
import org.springframework.boot.SpringApplication; // 引入 Spring Boot 启动工具。 
import org.springframework.boot.autoconfigure.SpringBootApplication; // 引入 Spring Boot 自动配置注解。 
import org.springframework.boot.builder.SpringApplicationBuilder; // 引入 war 包启动构建器。 
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer; // 引入传统 Servlet 容器启动基类。 
@SpringBootApplication // 开启 Spring Boot 自动配置、组件扫描和配置类能力。 
@MapperScan("com.example.petshop.mapper") // 扫描 MyBatis 和 MyBatis-Plus 的 Mapper 接口。 
public class PetShopApplication extends SpringBootServletInitializer { // 定义宠物商店应用主类并兼容 war 部署。 
    public static void main(String[] args) { // 定义 IntelliJ 直接运行的入口方法。 
        SpringApplication.run(PetShopApplication.class, args); // 启动 Spring Boot 应用。 
    } // 结束 main 方法。 
    @Override // 声明重写 Servlet 容器启动配置方法。 
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { // 定义外部 Tomcat 启动时使用的构建方法。 
        return builder.sources(PetShopApplication.class); // 返回注册了主类的 SpringApplicationBuilder。 
    } // 结束 configure 方法。 
} // 结束应用主类。 
