package com.example.petshop.controller; // 声明技术控制器所在包。 
import com.example.petshop.service.TechnologyShowcaseService; // 引入技术覆盖服务。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器返回 JSON 数据。 
@RequestMapping("/api/tech") // 指定技术学习 API 基础路径。 
public class TechnologyController { // 定义技术覆盖矩阵控制器。 
    private final TechnologyShowcaseService showcaseService; // 保存技术覆盖服务依赖。 
    public TechnologyController(TechnologyShowcaseService showcaseService) { this.showcaseService = showcaseService; } // 使用构造器注入技术覆盖服务。 
    @GetMapping("/matrix") // 映射技术覆盖矩阵接口。 
    public Map<String, Object> matrix() { // 处理矩阵查询请求。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("data", showcaseService.matrix()); // 写入技术覆盖矩阵数据。 
        return body; // 返回矩阵响应。 
    } // 结束矩阵查询接口。 
} // 结束技术控制器。 
