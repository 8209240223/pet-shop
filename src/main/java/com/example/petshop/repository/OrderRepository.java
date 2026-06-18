package com.example.petshop.repository; // 声明订单仓库所在包。 
import com.example.petshop.domain.PetOrder; // 引入订单实体。 
import org.springframework.data.jpa.repository.JpaRepository; // 引入 Spring Data JPA 仓库接口。 
import org.springframework.data.jpa.repository.Query; // 引入 JPA 自定义查询注解。 
import java.math.BigDecimal; // 引入精确金额类型。 
import java.util.List; // 引入列表接口。 
public interface OrderRepository extends JpaRepository<PetOrder, Long> { // 定义订单 JPA 仓库并继承通用 CRUD 能力。 
    List<PetOrder> findTop20ByOrderByCreatedAtDesc(); // 按时间倒序查询最近二十个订单。 
    List<PetOrder> findByStatusOrderByCreatedAtDesc(String status); // 按订单状态查询订单。 
    List<PetOrder> findByPhoneOrderByCreatedAtDesc(String phone); // 按客户电话查询订单。 
    long countByStatus(String status); // 按状态统计订单数量。 
    @Query("select coalesce(sum(o.totalAmount),0) from PetOrder o") // 使用 JPQL 统计订单总金额。 
    BigDecimal sumTotalAmount(); // 返回全部订单销售额。 
} // 结束订单仓库接口。 
