package com.example.petshop.controller; // 声明购物车控制器所在包。 
import com.example.petshop.service.CartService; // 引入购物车服务。 
import org.springframework.web.bind.annotation.DeleteMapping; // 引入 DELETE 映射注解。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
import org.springframework.web.bind.annotation.PathVariable; // 引入路径变量注解。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RequestParam; // 引入请求参数注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器返回 JSON。 
@RequestMapping("/api/cart") // 指定购物车 API 基础路径。 
public class CartRestController { // 定义购物车 REST 控制器。 
    private final CartService cartService; // 保存购物车服务依赖。 
    public CartRestController(CartService cartService) { this.cartService = cartService; } // 使用构造器注入购物车服务。 
    @GetMapping // 映射查询购物车接口。 
    public Map<String, Object> cart() { return body(); } // 返回当前会话购物车。 
    @PostMapping("/{productId}") // 映射加入购物车接口。 
    public Map<String, Object> add(@PathVariable Long productId, @RequestParam(defaultValue = "1") Integer quantity) { // 处理加入购物车请求。 
        cartService.add(productId, quantity); // 调用购物车服务添加商品。 
        return body(); // 返回最新购物车。 
    } // 结束加入购物车接口。 
    @DeleteMapping("/{productId}") // 映射移除购物车商品接口。 
    public Map<String, Object> remove(@PathVariable Long productId) { // 处理移除购物车请求。 
        cartService.remove(productId); // 调用购物车服务移除商品。 
        return body(); // 返回最新购物车。 
    } // 结束移除购物车接口。 
    @DeleteMapping // 映射清空购物车接口。 
    public Map<String, Object> clear() { // 处理清空购物车请求。 
        cartService.clear(); // 调用购物车服务清空数据。 
        return body(); // 返回空购物车。 
    } // 结束清空购物车接口。 
    private Map<String, Object> body() { // 定义购物车响应工具方法。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("items", cartService.all()); // 写入购物车行集合。 
        body.put("total", cartService.total()); // 写入购物车总金额。 
        return body; // 返回响应 Map。 
    } // 结束购物车响应工具方法。 
} // 结束购物车 REST 控制器。 
