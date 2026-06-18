package com.example.petshop.controller; // 声明认证控制器所在包。 
import com.example.petshop.service.AuthService; // 引入认证服务。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RequestParam; // 引入请求参数注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器返回 JSON。 
@RequestMapping("/api/auth") // 指定认证 API 基础路径。 
public class AuthRestController { // 定义认证 REST 控制器。 
    private final AuthService authService; // 保存认证服务依赖。 
    public AuthRestController(AuthService authService) { this.authService = authService; } // 使用构造器注入认证服务。 
    @PostMapping("/login") // 映射管理员登录接口。 
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) { // 处理登录请求。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("token", authService.login(username, password)); // 写入 JWT Token。 
        return body; // 返回登录响应。 
    } // 结束登录接口。 
} // 结束认证控制器。 
