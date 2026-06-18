package com.example.petshop.controller; // 声明传统数据访问控制器所在包。 
import com.example.petshop.domain.Customer; // 引入客户对象。 
import com.example.petshop.legacy.LegacyJdbcCustomerDao; // 引入原生 JDBC DAO。 
import org.springframework.jdbc.core.JdbcTemplate; // 引入 Spring JdbcTemplate。 
import org.springframework.web.bind.annotation.DeleteMapping; // 引入 DELETE 映射注解。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
import org.springframework.web.bind.annotation.PathVariable; // 引入路径变量注解。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.PutMapping; // 引入 PUT 映射注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RequestParam; // 引入请求参数注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestController // 声明该控制器返回 JSON。 
@RequestMapping("/api/legacy") // 指定传统技术演示 API 基础路径。 
public class LegacyDataController { // 定义传统数据访问控制器。 
    private final LegacyJdbcCustomerDao legacyJdbcCustomerDao; // 保存原生 JDBC DAO 依赖。 
    private final JdbcTemplate jdbcTemplate; // 保存 JdbcTemplate 依赖。 
    public LegacyDataController(LegacyJdbcCustomerDao legacyJdbcCustomerDao, JdbcTemplate jdbcTemplate) { this.legacyJdbcCustomerDao = legacyJdbcCustomerDao; this.jdbcTemplate = jdbcTemplate; } // 使用构造器注入依赖。 
    @GetMapping("/customers") // 映射原生 JDBC 查询客户接口。 
    public Map<String, Object> customers() throws Exception { return ok(legacyJdbcCustomerDao.findAll()); } // 返回原生 JDBC 查询结果。 
    @PostMapping("/customers") // 映射原生 JDBC 新增客户接口。 
    public Map<String, Object> createCustomer(@RequestParam String name, @RequestParam String phone, @RequestParam String email, @RequestParam String address) throws Exception { // 处理新增客户请求。 
        Customer customer = new Customer(); // 创建客户对象。 
        customer.setName(name); // 设置客户姓名。 
        customer.setPhone(phone); // 设置客户电话。 
        customer.setEmail(email); // 设置客户邮箱。 
        customer.setAddress(address); // 设置客户地址。 
        return ok(legacyJdbcCustomerDao.save(customer)); // 使用原生 JDBC 保存客户并返回结果。 
    } // 结束新增客户接口。 
    @GetMapping("/customers/phone/{phone}") // 映射原生 JDBC 按电话查询客户接口。 
    public Map<String, Object> customerByPhone(@PathVariable String phone) throws Exception { return ok(legacyJdbcCustomerDao.findByPhone(phone)); } // 返回按电话查询到的客户。 
    @PutMapping("/customers/{id}") // 映射原生 JDBC 更新客户接口。 
    public Map<String, Object> updateCustomer(@PathVariable Long id, @RequestParam String name, @RequestParam String phone, @RequestParam String email, @RequestParam String address) throws Exception { // 处理更新客户请求。 
        Customer customer = new Customer(); // 创建客户对象。 
        customer.setName(name); // 设置客户姓名。 
        customer.setPhone(phone); // 设置客户电话。 
        customer.setEmail(email); // 设置客户邮箱。 
        customer.setAddress(address); // 设置客户地址。 
        return ok(legacyJdbcCustomerDao.update(id, customer)); // 使用原生 JDBC 更新客户并返回结果。 
    } // 结束更新客户接口。 
    @DeleteMapping("/customers/{id}") // 映射原生 JDBC 删除客户接口。 
    public Map<String, Object> deleteCustomer(@PathVariable Long id) throws Exception { return ok(legacyJdbcCustomerDao.delete(id)); } // 删除客户并返回是否成功。 
    @GetMapping("/customers/count") // 映射原生 JDBC 客户数量统计接口。 
    public Map<String, Object> customerCount() throws Exception { return ok(legacyJdbcCustomerDao.count()); } // 返回原生 JDBC 统计的客户数量。 
    @GetMapping("/stats") // 映射 JdbcTemplate 统计接口。 
    public Map<String, Object> stats() { // 处理统计请求。 
        Map<String, Object> data = new LinkedHashMap<>(); // 创建统计结果 Map。 
        data.put("productCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class)); // 使用 JdbcTemplate 统计商品数。 
        data.put("customerCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customers", Integer.class)); // 使用 JdbcTemplate 统计客户数。 
        data.put("orderCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pet_orders", Integer.class)); // 使用 JdbcTemplate 统计订单数。 
        return ok(data); // 返回统计结果。 
    } // 结束统计接口。 
    @GetMapping("/jdbc-template/category-stats") // 映射 JdbcTemplate 分类统计接口。 
    public Map<String, Object> categoryStats() { return ok(jdbcTemplate.queryForList("SELECT category,COUNT(*) AS amount FROM products GROUP BY category ORDER BY category")); } // 使用 JdbcTemplate 统计每个分类商品数。 
    @GetMapping("/jdbc-template/low-stock-count") // 映射 JdbcTemplate 低库存数量接口。 
    public Map<String, Object> lowStockCount(@RequestParam(defaultValue = "5") Integer limit) { return ok(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products WHERE stock <= ?", Integer.class, limit)); } // 使用 JdbcTemplate 统计低库存数量。 
    @GetMapping("/jdbc-template/product-names") // 映射 JdbcTemplate 商品名称列表接口。 
    public Map<String, Object> productNames() { return ok(jdbcTemplate.queryForList("SELECT name FROM products ORDER BY id", String.class)); } // 使用 JdbcTemplate 查询商品名称列表。 
    @GetMapping("/jdbc-template/order-total") // 映射 JdbcTemplate 订单总额接口。 
    public Map<String, Object> orderTotal() { return ok(jdbcTemplate.queryForObject("SELECT COALESCE(SUM(total_amount),0) FROM pet_orders", java.math.BigDecimal.class)); } // 使用 JdbcTemplate 统计订单总额。 
    @GetMapping("/jdbc-template/customer-options") // 映射 JdbcTemplate 客户选项接口。 
    public Map<String, Object> customerOptions() { return ok(jdbcTemplate.queryForList("SELECT id,name,phone FROM customers ORDER BY id")); } // 使用 JdbcTemplate 查询客户下拉选项。 
    private Map<String, Object> ok(Object data) { // 定义统一响应工具方法。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", true); // 写入成功标记。 
        body.put("data", data); // 写入响应数据。 
        return body; // 返回响应 Map。 
    } // 结束统一响应工具方法。 
} // 结束传统数据访问控制器。 
