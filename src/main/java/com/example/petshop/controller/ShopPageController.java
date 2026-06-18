package com.example.petshop.controller; // 声明页面控制器所在包。 
import com.example.petshop.service.ProductService; // 引入商品服务。 
import com.example.petshop.legacy.LegacyJdbcCustomerDao; // 引入传统 JDBC 客户 DAO。 
import com.example.petshop.service.OrderService; // 引入订单服务。 
import com.example.petshop.service.TechnologyShowcaseService; // 引入技术覆盖服务。 
import org.springframework.stereotype.Controller; // 引入 MVC 控制器注解。 
import org.springframework.ui.Model; // 引入 Spring MVC 模型对象。 
import org.springframework.web.bind.annotation.GetMapping; // 引入 GET 映射注解。 
@Controller // 声明该类返回视图页面而不是 JSON。 
public class ShopPageController { // 定义传统 Spring MVC 页面控制器。 
    private final ProductService productService; // 保存商品服务依赖。 
    private final LegacyJdbcCustomerDao customerDao; // 保存传统 JDBC 客户 DAO 依赖。 
    private final OrderService orderService; // 保存订单服务依赖。 
    private final TechnologyShowcaseService showcaseService; // 保存技术覆盖服务依赖。 
    public ShopPageController(ProductService productService, LegacyJdbcCustomerDao customerDao, OrderService orderService, TechnologyShowcaseService showcaseService) { this.productService = productService; this.customerDao = customerDao; this.orderService = orderService; this.showcaseService = showcaseService; } // 使用构造器注入页面所需服务。 
    @GetMapping("/legacy/jsp-shop") // 映射传统 JSP 商店页面。 
    public String jspShop(Model model) { // 处理 JSP 页面请求。 
        model.addAttribute("products", productService.list(null)); // 将商品列表放入 Model。 
        model.addAttribute("title", "JSP 时代宠物商店"); // 将页面标题放入 Model。 
        return "shop"; // 返回 JSP 逻辑视图名。 
    } // 结束 JSP 页面请求。 
    @GetMapping("/legacy/jsp-customers") // 映射传统 JSP 客户页面。 
    public String jspCustomers(Model model) throws Exception { // 处理 JSP 客户页面请求。 
        model.addAttribute("customers", customerDao.findAll()); // 使用原生 JDBC 查询客户并放入 Model。 
        model.addAttribute("title", "JSP 客户管理"); // 将页面标题放入 Model。 
        return "customers"; // 返回客户 JSP 逻辑视图名。 
    } // 结束 JSP 客户页面请求。 
    @GetMapping("/legacy/jsp-orders") // 映射传统 JSP 订单页面。 
    public String jspOrders(Model model) { // 处理 JSP 订单页面请求。 
        model.addAttribute("orders", orderService.recent()); // 使用 JPA 查询订单并放入 Model。 
        model.addAttribute("title", "JSP 订单查看"); // 将页面标题放入 Model。 
        return "orders"; // 返回订单 JSP 逻辑视图名。 
    } // 结束 JSP 订单页面请求。 
    @GetMapping("/legacy/jsp-tech") // 映射传统 JSP 技术矩阵页面。 
    public String jspTech(Model model) { // 处理 JSP 技术矩阵页面请求。 
        model.addAttribute("matrix", showcaseService.matrix()); // 查询技术矩阵并放入 Model。 
        model.addAttribute("title", "JSP 技术覆盖矩阵"); // 将页面标题放入 Model。 
        return "tech"; // 返回技术矩阵 JSP 逻辑视图名。 
    } // 结束 JSP 技术矩阵页面请求。 
    @GetMapping("/legacy/jsp-admin") // 映射传统 JSP 管理入口页面。 
    public String jspAdmin(Model model) { // 处理 JSP 管理页面请求。 
        model.addAttribute("products", productService.lowStock(10)); // 查询低库存商品并放入 Model。 
        model.addAttribute("title", "JSP 管理入口"); // 将页面标题放入 Model。 
        return "admin"; // 返回管理 JSP 逻辑视图名。 
    } // 结束 JSP 管理页面请求。 
} // 结束页面控制器。 
