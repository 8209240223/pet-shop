package com.example.petshop.legacy; // 声明传统 JDBC DAO 所在包。 
import com.example.petshop.domain.Customer; // 引入客户对象。 
import org.springframework.stereotype.Repository; // 引入仓库组件注解。 
import javax.sql.DataSource; // 引入标准数据源接口。 
import java.sql.Connection; // 引入 JDBC 连接对象。 
import java.sql.PreparedStatement; // 引入 JDBC 预编译语句对象。 
import java.sql.ResultSet; // 引入 JDBC 结果集对象。 
import java.sql.Statement; // 引入 JDBC 普通语句对象。 
import java.util.ArrayList; // 引入数组列表实现。 
import java.util.List; // 引入列表接口。 
@Repository // 声明该类是传统 DAO 组件。 
public class LegacyJdbcCustomerDao { // 定义原生 JDBC 客户 DAO。 
    private final DataSource dataSource; // 保存由 Spring IoC 注入的数据源。 
    public LegacyJdbcCustomerDao(DataSource dataSource) { this.dataSource = dataSource; } // 通过构造器注入 DataSource。 
    public List<Customer> findAll() throws Exception { // 定义原生 JDBC 查询全部客户方法。 
        List<Customer> customers = new ArrayList<>(); // 创建客户结果集合。 
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT id,name,phone,email,address FROM customers ORDER BY id")) { // 获取连接、执行 SQL 并自动释放资源。 
            while (resultSet.next()) { // 循环读取结果集中的每一行。 
                Customer customer = new Customer(); // 创建客户对象。 
                customer.setId(resultSet.getLong("id")); // 从结果集中设置客户编号。 
                customer.setName(resultSet.getString("name")); // 从结果集中设置客户姓名。 
                customer.setPhone(resultSet.getString("phone")); // 从结果集中设置客户电话。 
                customer.setEmail(resultSet.getString("email")); // 从结果集中设置客户邮箱。 
                customer.setAddress(resultSet.getString("address")); // 从结果集中设置客户地址。 
                customers.add(customer); // 将客户加入结果集合。 
            } // 结束结果集循环。 
        } // 关闭 JDBC 资源。 
        return customers; // 返回客户集合。 
    } // 结束查询全部客户方法。 
    public Customer save(Customer customer) throws Exception { // 定义原生 JDBC 新增客户方法。 
        String sql = "INSERT INTO customers(name,phone,email,address) VALUES (?,?,?,?)"; // 定义新增客户 SQL。 
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 获取连接并创建可返回主键的预编译语句。 
            statement.setString(1, customer.getName()); // 绑定客户姓名参数。 
            statement.setString(2, customer.getPhone()); // 绑定客户电话参数。 
            statement.setString(3, customer.getEmail()); // 绑定客户邮箱参数。 
            statement.setString(4, customer.getAddress()); // 绑定客户地址参数。 
            statement.executeUpdate(); // 执行新增 SQL。 
            try (ResultSet keys = statement.getGeneratedKeys()) { // 读取数据库生成的主键。 
                if (keys.next()) { customer.setId(keys.getLong(1)); } // 如果存在主键则写回客户对象。 
            } // 关闭主键结果集。 
        } // 关闭 JDBC 资源。 
        return customer; // 返回带编号的客户对象。 
    } // 结束新增客户方法。 
    public Customer findByPhone(String phone) throws Exception { // 定义原生 JDBC 按电话查询客户方法。 
        String sql = "SELECT id,name,phone,email,address FROM customers WHERE phone = ?"; // 定义按电话查询客户 SQL。 
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) { // 获取连接并创建预编译查询语句。 
            statement.setString(1, phone); // 绑定电话查询参数。 
            try (ResultSet resultSet = statement.executeQuery()) { // 执行查询并读取结果集。 
                if (!resultSet.next()) { return null; } // 没有查询到客户时返回空。 
                Customer customer = new Customer(); // 创建客户对象。 
                customer.setId(resultSet.getLong("id")); // 设置客户编号。 
                customer.setName(resultSet.getString("name")); // 设置客户姓名。 
                customer.setPhone(resultSet.getString("phone")); // 设置客户电话。 
                customer.setEmail(resultSet.getString("email")); // 设置客户邮箱。 
                customer.setAddress(resultSet.getString("address")); // 设置客户地址。 
                return customer; // 返回查询到的客户。 
            } // 关闭结果集。 
        } // 关闭 JDBC 资源。 
    } // 结束按电话查询客户方法。 
    public Customer update(Long id, Customer customer) throws Exception { // 定义原生 JDBC 更新客户方法。 
        String sql = "UPDATE customers SET name=?,phone=?,email=?,address=? WHERE id=?"; // 定义更新客户 SQL。 
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) { // 获取连接并创建预编译更新语句。 
            statement.setString(1, customer.getName()); // 绑定客户姓名参数。 
            statement.setString(2, customer.getPhone()); // 绑定客户电话参数。 
            statement.setString(3, customer.getEmail()); // 绑定客户邮箱参数。 
            statement.setString(4, customer.getAddress()); // 绑定客户地址参数。 
            statement.setLong(5, id); // 绑定客户编号参数。 
            statement.executeUpdate(); // 执行更新 SQL。 
        } // 关闭 JDBC 资源。 
        customer.setId(id); // 将编号写回客户对象。 
        return customer; // 返回更新后的客户对象。 
    } // 结束更新客户方法。 
    public boolean delete(Long id) throws Exception { // 定义原生 JDBC 删除客户方法。 
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE id=?")) { // 获取连接并创建删除语句。 
            statement.setLong(1, id); // 绑定要删除的客户编号。 
            return statement.executeUpdate() == 1; // 返回是否删除了一行。 
        } // 关闭 JDBC 资源。 
    } // 结束删除客户方法。 
    public int count() throws Exception { // 定义原生 JDBC 统计客户数量方法。 
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customers")) { // 执行客户数量统计 SQL。 
            resultSet.next(); // 将游标移动到第一行。 
            return resultSet.getInt(1); // 返回客户数量。 
        } // 关闭 JDBC 资源。 
    } // 结束统计客户数量方法。 
} // 结束传统 JDBC DAO。 
