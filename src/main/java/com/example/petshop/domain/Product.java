package com.example.petshop.domain; // 声明商品实体所在包。 
import com.baomidou.mybatisplus.annotation.IdType; // 引入 MyBatis-Plus 主键策略。 
import com.baomidou.mybatisplus.annotation.TableId; // 引入 MyBatis-Plus 主键注解。 
import com.baomidou.mybatisplus.annotation.TableName; // 引入 MyBatis-Plus 表名注解。 
import javax.persistence.Column; // 引入 JPA 字段注解。 
import javax.persistence.Entity; // 引入 JPA 实体注解。 
import javax.persistence.GeneratedValue; // 引入 JPA 主键生成注解。 
import javax.persistence.GenerationType; // 引入 JPA 主键生成策略。 
import javax.persistence.Id; // 引入 JPA 主键注解。 
import javax.persistence.Table; // 引入 JPA 表名注解。 
import javax.validation.constraints.Min; // 引入最小值校验注解。 
import javax.validation.constraints.NotBlank; // 引入非空字符串校验注解。 
import javax.validation.constraints.NotNull; // 引入非空对象校验注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
@Entity // 声明该类也是 Hibernate/JPA 实体。 
@Table(name = "products") // 指定 JPA 映射到 products 表。 
@TableName("products") // 指定 MyBatis-Plus 映射到 products 表。 
public class Product { // 定义宠物商店商品 POJO。 
    @Id // 声明 JPA 主键字段。 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定数据库自增主键策略。 
    @TableId(type = IdType.AUTO) // 指定 MyBatis-Plus 自增主键策略。 
    private Long id; // 保存商品编号。 
    @NotBlank(message = "商品名称不能为空") // 校验商品名称不能为空。 
    private String name; // 保存商品名称。 
    @NotBlank(message = "商品分类不能为空") // 校验商品分类不能为空。 
    private String category; // 保存商品分类。 
    @NotNull(message = "商品价格不能为空") // 校验商品价格不能为空。 
    private BigDecimal price; // 保存商品价格。 
    @Min(value = 0, message = "库存不能小于 0") // 校验库存不能为负数。 
    private Integer stock; // 保存商品库存。 
    @Column(length = 500) // 指定商品描述字段长度。 
    private String description; // 保存商品描述。 
    @Column(name = "image_url") // 指定图片地址字段名。 
    private String imageUrl; // 保存商品图片地址。 
    private Boolean featured; // 保存是否首页推荐。 
    public Long getId() { return id; } // 返回商品编号。 
    public void setId(Long id) { this.id = id; } // 设置商品编号。 
    public String getName() { return name; } // 返回商品名称。 
    public void setName(String name) { this.name = name; } // 设置商品名称。 
    public String getCategory() { return category; } // 返回商品分类。 
    public void setCategory(String category) { this.category = category; } // 设置商品分类。 
    public BigDecimal getPrice() { return price; } // 返回商品价格。 
    public void setPrice(BigDecimal price) { this.price = price; } // 设置商品价格。 
    public Integer getStock() { return stock; } // 返回商品库存。 
    public void setStock(Integer stock) { this.stock = stock; } // 设置商品库存。 
    public String getDescription() { return description; } // 返回商品描述。 
    public void setDescription(String description) { this.description = description; } // 设置商品描述。 
    public String getImageUrl() { return imageUrl; } // 返回商品图片地址。 
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; } // 设置商品图片地址。 
    public Boolean getFeatured() { return featured; } // 返回是否推荐。 
    public void setFeatured(Boolean featured) { this.featured = featured; } // 设置是否推荐。 
} // 结束商品实体类。 
