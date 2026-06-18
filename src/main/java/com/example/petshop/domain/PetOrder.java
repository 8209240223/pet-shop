package com.example.petshop.domain; // 声明订单实体所在包。 
import javax.persistence.CascadeType; // 引入 JPA 级联操作类型。 
import javax.persistence.Entity; // 引入 JPA 实体注解。 
import javax.persistence.FetchType; // 引入 JPA 抓取策略。 
import javax.persistence.GeneratedValue; // 引入 JPA 主键生成注解。 
import javax.persistence.GenerationType; // 引入 JPA 主键生成策略。 
import javax.persistence.Id; // 引入 JPA 主键注解。 
import javax.persistence.OneToMany; // 引入 JPA 一对多注解。 
import javax.persistence.Table; // 引入 JPA 表名注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.time.LocalDateTime; // 引入日期时间类型。 
import java.util.ArrayList; // 引入数组列表。 
import java.util.List; // 引入列表接口。 
@Entity // 声明该类是 Hibernate 管理的实体。 
@Table(name = "pet_orders") // 指定实体映射到订单表。 
public class PetOrder { // 定义宠物商店订单实体。 
    @Id // 声明订单主键。 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定订单主键自增。 
    private Long id; // 保存订单编号。 
    private String customerName; // 保存下单人姓名。 
    private String phone; // 保存下单人电话。 
    private BigDecimal totalAmount; // 保存订单总金额。 
    private String status; // 保存订单状态。 
    private LocalDateTime createdAt; // 保存下单时间。 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // 配置订单到明细的一对多关系。 
    private List<OrderItem> items = new ArrayList<>(); // 保存订单明细集合。 
    public Long getId() { return id; } // 返回订单编号。 
    public void setId(Long id) { this.id = id; } // 设置订单编号。 
    public String getCustomerName() { return customerName; } // 返回下单人姓名。 
    public void setCustomerName(String customerName) { this.customerName = customerName; } // 设置下单人姓名。 
    public String getPhone() { return phone; } // 返回下单人电话。 
    public void setPhone(String phone) { this.phone = phone; } // 设置下单人电话。 
    public BigDecimal getTotalAmount() { return totalAmount; } // 返回订单总金额。 
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; } // 设置订单总金额。 
    public String getStatus() { return status; } // 返回订单状态。 
    public void setStatus(String status) { this.status = status; } // 设置订单状态。 
    public LocalDateTime getCreatedAt() { return createdAt; } // 返回下单时间。 
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // 设置下单时间。 
    public List<OrderItem> getItems() { return items; } // 返回订单明细集合。 
    public void setItems(List<OrderItem> items) { this.items = items; } // 设置订单明细集合。 
} // 结束订单实体。 
