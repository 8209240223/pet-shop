package com.example.petshop.service; // 声明购物车服务所在包。 
import com.example.petshop.domain.Product; // 引入商品实体。 
import org.springframework.stereotype.Service; // 引入服务组件注解。 
import org.springframework.web.context.annotation.SessionScope; // 引入会话作用域注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.util.Collection; // 引入集合接口。 
import java.util.LinkedHashMap; // 引入保持顺序的 Map。 
import java.util.Map; // 引入 Map 接口。 
@Service // 声明该类由 Spring IoC 容器管理。 
@SessionScope // 声明每个浏览器会话拥有独立购物车。 
public class CartService { // 定义会话级购物车服务。 
    private final ProductService productService; // 保存商品服务依赖。 
    private final Map<Long, CartLine> lines = new LinkedHashMap<>(); // 保存购物车行项目。 
    public CartService(ProductService productService) { this.productService = productService; } // 使用构造器注入商品服务。 
    public Collection<CartLine> all() { return lines.values(); } // 返回全部购物车行。 
    public void add(Long productId, Integer quantity) { // 定义加入购物车方法。 
        Product product = productService.get(productId); // 查询要加入的商品。 
        if (product == null) { throw new IllegalArgumentException("商品不存在"); } // 商品不存在时抛出异常。 
        CartLine line = lines.computeIfAbsent(productId, key -> new CartLine(product, 0)); // 获取或创建购物车行。 
        line.quantity = line.quantity + Math.max(quantity, 1); // 累加购买数量并保证至少加一件。 
    } // 结束加入购物车方法。 
    public void remove(Long productId) { lines.remove(productId); } // 从购物车删除商品。 
    public void clear() { lines.clear(); } // 清空购物车。 
    public BigDecimal total() { return lines.values().stream().map(CartLine::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add); } // 汇总购物车总金额。 
    public static class CartLine { // 定义购物车行数据对象。 
        private Product product; // 保存商品对象。 
        private Integer quantity; // 保存购买数量。 
        public CartLine(Product product, Integer quantity) { this.product = product; this.quantity = quantity; } // 初始化购物车行。 
        public Product getProduct() { return product; } // 返回商品对象。 
        public Integer getQuantity() { return quantity; } // 返回购买数量。 
        public BigDecimal getSubtotal() { return subtotal(); } // 返回小计金额。 
        private BigDecimal subtotal() { return product.getPrice().multiply(BigDecimal.valueOf(quantity)); } // 计算小计金额。 
    } // 结束购物车行对象。 
} // 结束购物车服务。 
