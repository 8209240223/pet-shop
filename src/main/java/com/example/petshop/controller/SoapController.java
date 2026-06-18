package com.example.petshop.controller; // 声明 SOAP 控制器所在包。 
import com.example.petshop.domain.Product; // 引入商品实体。 
import com.example.petshop.service.ProductService; // 引入商品服务。 
import org.springframework.http.MediaType; // 引入媒体类型常量。 
import org.springframework.web.bind.annotation.PostMapping; // 引入 POST 映射注解。 
import org.springframework.web.bind.annotation.RequestBody; // 引入请求体注解。 
import org.springframework.web.bind.annotation.RequestMapping; // 引入请求路径注解。 
import org.springframework.web.bind.annotation.RestController; // 引入 REST 控制器注解。 
@RestController // 声明该控制器直接返回 XML 字符串。 
@RequestMapping("/api/soap") // 指定 SOAP 风格 API 基础路径。 
public class SoapController { // 定义 SOAP 风格 Web Service 演示控制器。 
    private final ProductService productService; // 保存商品服务依赖。 
    public SoapController(ProductService productService) { this.productService = productService; } // 使用构造器注入商品服务。 
    @PostMapping(value = "/pet-service", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE) // 映射 SOAP XML 请求接口。 
    public String petService(@RequestBody String envelope) { // 处理 SOAP Envelope 字符串。 
        if (envelope.contains("<listPets")) { return wrap("<listPetsResponse>" + productService.list(null).stream().map(product -> "<pet><name>" + escape(product.getName()) + "</name></pet>").reduce("", String::concat) + "</listPetsResponse>"); } // SOAP 功能一：返回商品列表。 
        if (envelope.contains("<featuredPets")) { return wrap("<featuredPetsResponse>" + productService.featured().stream().map(product -> "<pet><name>" + escape(product.getName()) + "</name></pet>").reduce("", String::concat) + "</featuredPetsResponse>"); } // SOAP 功能二：返回推荐商品。 
        if (envelope.contains("<lowStockPets")) { return wrap("<lowStockPetsResponse>" + productService.lowStock(5).stream().map(product -> "<pet><name>" + escape(product.getName()) + "</name><stock>" + product.getStock() + "</stock></pet>").reduce("", String::concat) + "</lowStockPetsResponse>"); } // SOAP 功能三：返回低库存商品。 
        if (envelope.contains("<categories")) { return wrap("<categoriesResponse>" + productService.categories().stream().map(category -> "<category>" + escape(category) + "</category>").reduce("", String::concat) + "</categoriesResponse>"); } // SOAP 功能四：返回商品分类。 
        Long id = envelope.contains("<id>") ? Long.valueOf(envelope.replaceAll("(?s).*<id>(\\d+)</id>.*", "$1")) : 1L; // 从 XML 中取出商品编号。 
        Product product = productService.get(id); // 查询商品对象。 
        String name = product == null ? "不存在" : product.getName(); // 生成商品名称响应值。 
        String price = product == null ? "0" : product.getPrice().toPlainString(); // 生成商品价格响应值。 
        return wrap("<getPetResponse><name>" + escape(name) + "</name><price>" + price + "</price></getPetResponse>"); // SOAP 功能五：返回商品详情。 
    } // 结束 SOAP 接口。 
    private String wrap(String body) { return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>" + body + "</soap:Body></soap:Envelope>"; } // 包装 SOAP Envelope 响应。 
    private String escape(String text) { return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"); } // 转义 XML 特殊字符。 
} // 结束 SOAP 控制器。 
