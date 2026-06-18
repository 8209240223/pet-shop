package com.example.petshop.service; // 声明技术覆盖服务所在包。 
import org.springframework.stereotype.Service; // 引入服务组件注解。 
import java.util.ArrayList; // 引入数组列表。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.List; // 引入列表接口。 
import java.util.Map; // 引入 Map 接口。 
@Service // 声明该类是 Spring IoC 容器管理的服务。 
public class TechnologyShowcaseService { // 定义技术覆盖矩阵服务。 
    public List<Map<String, Object>> matrix() { // 定义返回全部技术覆盖矩阵的方法。 
        List<Map<String, Object>> rows = new ArrayList<>(); // 创建矩阵行集合。 
        rows.add(row("Servlet / Struts思想", "传统前端控制器", "/legacy/servlet-mvc?action=home", "/legacy/servlet-mvc?action=products", "/legacy/servlet-mvc?action=customers", "/legacy/servlet-mvc?action=orders", "/legacy/servlet-mvc?action=stats")); // 添加 Servlet 五个功能入口。 
        rows.add(row("JSP / JSTL", "服务端渲染页面", "/legacy/jsp-shop", "/legacy/jsp-customers", "/legacy/jsp-orders", "/legacy/jsp-tech", "/legacy/jsp-admin")); // 添加 JSP 五个功能入口。 
        rows.add(row("原生 JDBC", "手写 Connection/Statement/ResultSet", "/api/legacy/customers", "/api/legacy/customers/phone/13800000001", "/api/legacy/customers?POST", "/api/legacy/customers/1?PUT", "/api/legacy/customers/count")); // 添加 JDBC 五个功能入口。 
        rows.add(row("JdbcTemplate", "Spring 简化 JDBC", "/api/legacy/stats", "/api/legacy/jdbc-template/category-stats", "/api/legacy/jdbc-template/low-stock-count", "/api/legacy/jdbc-template/product-names", "/api/legacy/jdbc-template/order-total")); // 添加 JdbcTemplate 五个功能入口。 
        rows.add(row("MyBatis XML", "XML SQL 映射", "/api/products", "/api/products/categories", "/api/products/category/用品", "/api/products/low-stock", "/api/products/price-range?min=1&max=500")); // 添加 MyBatis XML 五个功能入口。 
        rows.add(row("MyBatis-Plus", "BaseMapper 增强 CRUD", "/api/products/featured", "/api/products/1", "/api/admin/products?POST", "/api/admin/products/1?PUT", "/api/admin/products/1?DELETE")); // 添加 MyBatis-Plus 五个功能入口。 
        rows.add(row("Hibernate / JPA", "实体关系和仓库查询", "/api/orders", "/api/orders/status/已创建", "/api/orders/phone/13800000000", "/api/orders/status/已创建/count", "/api/orders/sales-total")); // 添加 JPA 五个功能入口。 
        rows.add(row("Spring MVC", "Controller 映射和 Model", "/api/products", "/api/cart", "/api/orders", "/legacy/jsp-shop", "/api/tech/matrix")); // 添加 Spring MVC 五个功能入口。 
        rows.add(row("Spring AOP", "服务层日志切面", "ProductService.list", "CartService.add", "OrderService.checkout", "AuthService.login", "TechnologyShowcaseService.matrix")); // 添加 AOP 五个功能入口。 
        rows.add(row("RESTful API", "HTTP 动词和 JSON", "GET /api/products", "POST /api/cart/{id}", "POST /api/orders", "PUT /api/orders/{id}/status", "DELETE /api/cart/{id}")); // 添加 REST 五个功能入口。 
        rows.add(row("SOAP / XML", "Envelope 风格 WebService", "<getPet>", "<listPets>", "<featuredPets>", "<lowStockPets>", "<categories>")); // 添加 SOAP 五个功能入口。 
        rows.add(row("JWT / Interceptor", "管理员权限保护", "POST /api/auth/login", "POST /api/admin/products", "PUT /api/admin/products/{id}", "DELETE /api/admin/products/{id}", "Authorization: Bearer token")); // 添加 JWT 五个功能入口。 
        rows.add(row("ES6 Module", "前端模块化", "api.js", "store.js", "app.js 商品", "app.js 购物车", "app.js 技术实验")); // 添加 ES6 Module 五个功能入口。 
        rows.add(row("CommonJS / Node / NPM", "旧模块和工程化", "price-commonjs.cjs", "cart-commonjs.cjs", "package.json scripts", "webpack.config.cjs", "npm run webpack")); // 添加 CommonJS 五个功能入口。 
        rows.add(row("webpack / Vite", "构建工具", "webpack entry", "webpack output", "vite root", "vite proxy", "vite build")); // 添加构建工具五个功能入口。 
        rows.add(row("Vue / React / Angular", "现代框架依赖对比", "package.json vue", "package.json react", "package.json react-dom", "package.json @angular/core", "README 扩展说明")); // 添加现代前端框架五个功能入口。 
        return rows; // 返回完整技术矩阵。 
    } // 结束矩阵方法。 
    private Map<String, Object> row(String technology, String purpose, String one, String two, String three, String four, String five) { // 定义创建矩阵行的工具方法。 
        Map<String, Object> row = new LinkedHashMap<>(); // 创建有序矩阵行。 
        row.put("technology", technology); // 写入技术名称。 
        row.put("purpose", purpose); // 写入技术用途。 
        row.put("features", List.of(one, two, three, four, five)); // 写入五个功能入口。 
        return row; // 返回矩阵行。 
    } // 结束创建矩阵行方法。 
} // 结束技术覆盖矩阵服务。 
