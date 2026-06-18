package com.example.petshop.mapper; // 声明 MyBatis Mapper 所在包。 
import com.baomidou.mybatisplus.core.mapper.BaseMapper; // 引入 MyBatis-Plus 通用 Mapper。 
import com.example.petshop.domain.Product; // 引入商品实体。 
import org.apache.ibatis.annotations.Mapper; // 引入 MyBatis Mapper 注解。 
import org.apache.ibatis.annotations.Param; // 引入 MyBatis 参数命名注解。 
import java.util.List; // 引入列表接口。 
@Mapper // 声明该接口由 MyBatis 创建代理对象。 
public interface ProductMapper extends BaseMapper<Product> { // 定义同时支持 MyBatis XML 和 MyBatis-Plus 的商品 Mapper。 
    List<Product> selectVisibleProducts(); // 使用 XML 查询全部商品。 
    List<Product> searchByKeyword(@Param("keyword") String keyword); // 使用 XML 按关键字查询商品。 
    List<Product> selectByCategory(@Param("category") String category); // 使用 XML 按分类查询商品。 
    List<Product> selectLowStock(@Param("limit") Integer limit); // 使用 XML 查询低库存商品。 
    List<Product> selectByPriceRange(@Param("min") java.math.BigDecimal min, @Param("max") java.math.BigDecimal max); // 使用 XML 按价格区间查询商品。 
    List<String> selectCategories(); // 使用 XML 查询全部商品分类。 
    int countByCategory(@Param("category") String category); // 使用 XML 统计某个分类的商品数量。 
    int updateStock(@Param("id") Long id, @Param("quantity") Integer quantity); // 使用 XML 扣减库存。 
} // 结束商品 Mapper。 
