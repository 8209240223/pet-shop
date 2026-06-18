package com.example.petshop.controller; // 声明订单控制器所在包。 
import com.example.petshop.service.OrderService; // 引入订单服务。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
import org.springframework.web.bind.annotation.PathVariable; // 引入路径变量注解。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.PutMapping; // 引入 PUT 映射注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RequestParam; // 引入请求参数注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器返回 JSON 数据。 
@RequestMapping("/api/orders") // 指定订单 API 基础路径。 
public class OrderRestController { // 定义订单 REST 控制器。 
    private final OrderService orderService; // 保存订单服务依赖。 
    public OrderRestController(OrderService orderService) { this.orderService = orderService; } // 使用构造器注入订单服务。 
    @GetMapping // 映射最近订单查询接口。 
    public Map<String, Object> recent() { return ok(orderService.recent()); } // 返回最近订单列表。 
    @GetMapping("/status/{status}") // 映射按状态查询订单接口。 
    public Map<String, Object> byStatus(@PathVariable String status) { return ok(orderService.byStatus(status)); } // 返回指定状态订单列表。 
    @GetMapping("/phone/{phone}") // 映射按电话查询订单接口。 
    public Map<String, Object> byPhone(@PathVariable String phone) { return ok(orderService.byPhone(phone)); } // 返回指定电话关联订单列表。 
    @GetMapping("/status/{status}/count") // 映射按状态统计订单接口。 
    public Map<String, Object> countByStatus(@PathVariable String status) { return ok(orderService.countByStatus(status)); } // 返回指定状态订单数量。 
    @GetMapping("/sales-total") // 映射订单销售额统计接口。 
    public Map<String, Object> totalSales() { return ok(orderService.totalSales()); } // 返回全部订单销售额。 
    @PutMapping("/{id}/status") // 映射更新订单状态接口。 
    public Map<String, Object> updateStatus(@PathVariable Long id, @RequestParam String status) { return ok(orderService.updateStatus(id, status)); } // 更新订单状态并返回订单。 
    @PostMapping // 映射购物车结算接口。 
    public Map<String, Object> checkout(@RequestParam String customerName, @RequestParam String phone) { // 处理结算请求。 
        return ok(orderService.checkout(customerName, phone)); // 创建订单并返回结果。 
    } // 结束结算接口。 
    private Map<String, Object> ok(Object data) { // 定义统一响应工具方法。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("data", data); // 写入响应数据。 
        return body; // 返回响应 Map。 
    } // 结束统一响应工具方法。 
} // 结束订单 REST 控制器。 
