package com.example.petshop.domain; // 声明订单明细实体所在包。 
import com.fasterxml.jackson.annotation.JsonIgnore; // 引入 JSON 忽略注解防止循环序列化。 
import javax.persistence.Entity; // 引入 JPA 实体注解。 
import javax.persistence.GeneratedValue; // 引入 JPA 主键生成注解。 
import javax.persistence.GenerationType; // 引入 JPA 主键生成策略。 
import javax.persistence.Id; // 引入 JPA 主键注解。 
import javax.persistence.JoinColumn; // 引入 JPA 外键列注解。 
import javax.persistence.ManyToOne; // 引入 JPA 多对一注解。 
import javax.persistence.Table; // 引入 JPA 表名注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
@Entity // 声明该类是 Hibernate 管理的实体。 
@Table(name = "order_items") // 指定实体映射到订单明细表。 
public class OrderItem { // 定义订单明细实体。 
    @Id // 声明订单明细主键。 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定主键自增策略。 
    private Long id; // 保存订单明细编号。 
    private Long productId; // 保存商品编号。 
    private String productName; // 保存商品名称快照。 
    private Integer quantity; // 保存购买数量。 
    private BigDecimal price; // 保存下单价格快照。 
    @JsonIgnore // JSON 输出时忽略反向订单对象。 
    @ManyToOne // 声明多条明细属于一个订单。 
    @JoinColumn(name = "order_id") // 指定订单外键列。 
    private PetOrder order; // 保存所属订单对象。 
    public Long getId() { return id; } // 返回明细编号。 
    public void setId(Long id) { this.id = id; } // 设置明细编号。 
    public Long getProductId() { return productId; } // 返回商品编号。 
    public void setProductId(Long productId) { this.productId = productId; } // 设置商品编号。 
    public String getProductName() { return productName; } // 返回商品名称。 
    public void setProductName(String productName) { this.productName = productName; } // 设置商品名称。 
    public Integer getQuantity() { return quantity; } // 返回购买数量。 
    public void setQuantity(Integer quantity) { this.quantity = quantity; } // 设置购买数量。 
    public BigDecimal getPrice() { return price; } // 返回商品价格。 
    public void setPrice(BigDecimal price) { this.price = price; } // 设置商品价格。 
    public PetOrder getOrder() { return order; } // 返回所属订单。 
    public void setOrder(PetOrder order) { this.order = order; } // 设置所属订单。 
} // 结束订单明细实体。 
