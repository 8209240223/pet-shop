package com.example.petshop.service; // 声明商品服务所在包。 
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; // 引入 MyBatis-Plus 查询条件对象。 
import com.example.petshop.domain.Product; // 引入商品实体。 
import com.example.petshop.mapper.ProductMapper; // 引入商品 Mapper。 
import org.springframework.stereotype.Service; // 引入服务组件注解。 
import org.springframework.transaction.annotation.Transactional; // 引入事务注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.util.List; // 引入列表接口。 
@Service // 声明该类是 Spring Service Bean。 
public class ProductService { // 定义商品业务服务。 
    private final ProductMapper productMapper; // 保存由 Spring IoC 注入的商品 Mapper。 
    public ProductService(ProductMapper productMapper) { this.productMapper = productMapper; } // 使用构造器注入 Mapper。 
    public List<Product> list(String keyword) { // 定义查询商品列表方法。 
        if (keyword == null || keyword.trim().isEmpty()) { return productMapper.selectVisibleProducts(); } // 关键字为空时调用 XML 查询全部商品。 
        return productMapper.searchByKeyword(keyword.trim()); // 关键字不为空时调用 XML 模糊查询。 
    } // 结束商品列表方法。 
    public List<Product> featured() { return productMapper.selectList(new QueryWrapper<Product>().eq("featured", true).orderByAsc("id")); } // 使用 MyBatis-Plus 查询推荐商品。 
    public List<Product> byCategory(String category) { return productMapper.selectByCategory(category); } // 使用 MyBatis XML 按分类查询商品。 
    public List<Product> lowStock(Integer limit) { return productMapper.selectLowStock(limit == null ? 5 : limit); } // 使用 MyBatis XML 查询低库存商品。 
    public List<Product> byPriceRange(BigDecimal min, BigDecimal max) { return productMapper.selectByPriceRange(min, max); } // 使用 MyBatis XML 查询价格区间商品。 
    public List<String> categories() { return productMapper.selectCategories(); } // 使用 MyBatis XML 查询分类列表。 
    public int countByCategory(String category) { return productMapper.countByCategory(category); } // 使用 MyBatis XML 统计分类数量。 
    public Product get(Long id) { return productMapper.selectById(id); } // 使用 MyBatis-Plus 按主键查询商品。 
    public Product save(Product product) { productMapper.insert(product); return product; } // 使用 MyBatis-Plus 新增商品并返回对象。 
    public Product update(Long id, Product product) { product.setId(id); productMapper.updateById(product); return productMapper.selectById(id); } // 使用 MyBatis-Plus 更新商品并返回最新数据。 
    public void delete(Long id) { productMapper.deleteById(id); } // 使用 MyBatis-Plus 删除商品。 
    @Transactional // 声明库存扣减需要事务保护。 
    public void decreaseStock(Long id, Integer quantity) { // 定义扣减库存方法。 
        int changed = productMapper.updateStock(id, quantity); // 调用 MyBatis XML SQL 扣减库存。 
        if (changed != 1) { throw new IllegalStateException("库存不足或商品不存在"); } // 扣减失败时抛出业务异常。 
    } // 结束扣减库存方法。 
} // 结束商品业务服务。 
