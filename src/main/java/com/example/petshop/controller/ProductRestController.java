package com.example.petshop.controller; // 声明商品 REST 控制器所在包。 
import com.example.petshop.domain.Product; // 引入商品实体。 
import com.example.petshop.service.ProductService; // 引入商品服务。 
import org.springframework.web.bind.annotation.DeleteMapping; // 引入 DELETE 映射注解。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
import org.springframework.web.bind.annotation.PathVariable; // 引入路径变量注解。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.PutMapping; // 引入 PUT 映射注解。 
import org.springframework.web.bind.annotation.RequestBody; // 引入请求体注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RequestParam; // 引入请求参数注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import javax.validation.Valid; // 引入参数校验注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器直接返回 JSON 数据。 
@RequestMapping("/api") // 指定 REST API 基础路径。 
public class ProductRestController { // 定义商品 REST 控制器。 
    private final ProductService productService; // 保存商品服务依赖。 
    public ProductRestController(ProductService productService) { this.productService = productService; } // 使用构造器注入商品服务。 
    @GetMapping("/products") // 映射查询商品列表接口。 
    public Map<String, Object> products(@RequestParam(required = false) String keyword) { // 处理商品列表查询请求。 
        return ok(productService.list(keyword)); // 返回统一 JSON 响应。 
    } // 结束查询商品列表接口。 
    @GetMapping("/products/{id}") // 映射查询商品详情接口。 
    public Map<String, Object> product(@PathVariable Long id) { // 处理商品详情查询请求。 
        return ok(productService.get(id)); // 返回指定商品数据。 
    } // 结束查询商品详情接口。 
    @GetMapping("/products/featured") // 映射推荐商品接口。 
    public Map<String, Object> featured() { return ok(productService.featured()); } // 返回 MyBatis-Plus 查询到的推荐商品。 
    @GetMapping("/products/categories") // 映射商品分类列表接口。 
    public Map<String, Object> categories() { return ok(productService.categories()); } // 返回 MyBatis XML 查询到的分类列表。 
    @GetMapping("/products/category/{category}") // 映射按分类查询商品接口。 
    public Map<String, Object> byCategory(@PathVariable String category) { return ok(productService.byCategory(category)); } // 返回某个分类下的商品。 
    @GetMapping("/products/low-stock") // 映射低库存商品接口。 
    public Map<String, Object> lowStock(@RequestParam(defaultValue = "5") Integer limit) { return ok(productService.lowStock(limit)); } // 返回低库存商品列表。 
    @GetMapping("/products/price-range") // 映射价格区间查询接口。 
    public Map<String, Object> priceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) { return ok(productService.byPriceRange(min, max)); } // 返回指定价格区间的商品。 
    @GetMapping("/products/category/{category}/count") // 映射分类商品数量接口。 
    public Map<String, Object> categoryCount(@PathVariable String category) { return ok(productService.countByCategory(category)); } // 返回指定分类的商品数量。 
    @PostMapping("/admin/products") // 映射管理员新增商品接口。 
    public Map<String, Object> create(@Valid @RequestBody Product product) { // 处理新增商品请求并校验参数。 
        return ok(productService.save(product)); // 保存商品并返回结果。 
    } // 结束新增商品接口。 
    @PutMapping("/admin/products/{id}") // 映射管理员更新商品接口。 
    public Map<String, Object> update(@PathVariable Long id, @Valid @RequestBody Product product) { // 处理更新商品请求。 
        return ok(productService.update(id, product)); // 更新商品并返回结果。 
    } // 结束更新商品接口。 
    @DeleteMapping("/admin/products/{id}") // 映射管理员删除商品接口。 
    public Map<String, Object> delete(@PathVariable Long id) { // 处理删除商品请求。 
        productService.delete(id); // 调用服务删除商品。 
        return ok("删除成功"); // 返回删除成功消息。 
    } // 结束删除商品接口。 
    private Map<String, Object> ok(Object data) { // 定义统一成功响应工具方法。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("data", data); // 写入响应数据。 
        return body; // 返回响应 Map。 
    } // 结束统一响应工具方法。 
} // 结束商品 REST 控制器。 
