package com.example.petshop.service; // 声明订单服务所在包。 
import com.example.petshop.domain.OrderItem; // 引入订单明细实体。 
import com.example.petshop.domain.PetOrder; // 引入订单实体。 
import com.example.petshop.repository.OrderRepository; // 引入订单 JPA 仓库。 
import org.springframework.stereotype.Service; // 引入服务组件注解。 
import org.springframework.transaction.annotation.Transactional; // 引入事务注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.time.LocalDateTime; // 引入日期时间类型。 
import java.util.List; // 引入列表接口。 
@Service // 声明该类是 Spring Service Bean。 
public class OrderService { // 定义订单业务服务。 
    private final CartService cartService; // 保存购物车服务依赖。 
    private final ProductService productService; // 保存商品服务依赖。 
    private final OrderRepository orderRepository; // 保存 JPA 订单仓库依赖。 
    public OrderService(CartService cartService, ProductService productService, OrderRepository orderRepository) { this.cartService = cartService; this.productService = productService; this.orderRepository = orderRepository; } // 使用构造器注入三个依赖。 
    public List<PetOrder> recent() { return orderRepository.findTop20ByOrderByCreatedAtDesc(); } // 查询最近订单列表。 
    public List<PetOrder> byStatus(String status) { return orderRepository.findByStatusOrderByCreatedAtDesc(status); } // 使用 JPA 方法名查询按状态查订单。 
    public List<PetOrder> byPhone(String phone) { return orderRepository.findByPhoneOrderByCreatedAtDesc(phone); } // 使用 JPA 方法名查询按电话查订单。 
    public long countByStatus(String status) { return orderRepository.countByStatus(status); } // 使用 JPA 方法名统计状态订单数。 
    public BigDecimal totalSales() { return orderRepository.sumTotalAmount(); } // 使用 JPQL 查询订单销售额。 
    public PetOrder updateStatus(Long id, String status) { PetOrder order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("订单不存在")); order.setStatus(status); return orderRepository.save(order); } // 使用 JPA 查询并更新订单状态。 
    @Transactional // 声明下单过程需要事务保护。 
    public PetOrder checkout(String customerName, String phone) { // 定义从购物车结算生成订单的方法。 
        if (cartService.all().isEmpty()) { throw new IllegalStateException("购物车为空"); } // 购物车为空时阻止下单。 
        PetOrder order = new PetOrder(); // 创建订单对象。 
        order.setCustomerName(customerName); // 设置下单人姓名。 
        order.setPhone(phone); // 设置下单人电话。 
        order.setStatus("已创建"); // 设置订单初始状态。 
        order.setCreatedAt(LocalDateTime.now()); // 设置当前下单时间。 
        order.setTotalAmount(cartService.total()); // 设置订单总金额。 
        for (CartService.CartLine line : cartService.all()) { // 遍历购物车中的每个商品。 
            productService.decreaseStock(line.getProduct().getId(), line.getQuantity()); // 扣减商品库存。 
            OrderItem item = new OrderItem(); // 创建订单明细对象。 
            item.setOrder(order); // 设置明细所属订单。 
            item.setProductId(line.getProduct().getId()); // 设置商品编号快照。 
            item.setProductName(line.getProduct().getName()); // 设置商品名称快照。 
            item.setPrice(line.getProduct().getPrice()); // 设置商品价格快照。 
            item.setQuantity(line.getQuantity()); // 设置购买数量。 
            order.getItems().add(item); // 将明细加入订单集合。 
        } // 结束购物车遍历。 
        PetOrder saved = orderRepository.save(order); // 使用 Spring Data JPA 保存订单和明细。 
        cartService.clear(); // 下单成功后清空购物车。 
        return saved; // 返回保存后的订单。 
    } // 结束结算方法。 
} // 结束订单服务。 
