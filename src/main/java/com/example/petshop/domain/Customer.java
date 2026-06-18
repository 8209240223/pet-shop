package com.example.petshop.domain; // 声明客户对象所在包。 
public class Customer { // 定义用于 JDBC 演示的客户 POJO。 
    private Long id; // 保存客户编号。 
    private String name; // 保存客户姓名。 
    private String phone; // 保存客户电话。 
    private String email; // 保存客户邮箱。 
    private String address; // 保存客户地址。 
    public Long getId() { return id; } // 返回客户编号。 
    public void setId(Long id) { this.id = id; } // 设置客户编号。 
    public String getName() { return name; } // 返回客户姓名。 
    public void setName(String name) { this.name = name; } // 设置客户姓名。 
    public String getPhone() { return phone; } // 返回客户电话。 
    public void setPhone(String phone) { this.phone = phone; } // 设置客户电话。 
    public String getEmail() { return email; } // 返回客户邮箱。 
    public void setEmail(String email) { this.email = email; } // 设置客户邮箱。 
    public String getAddress() { return address; } // 返回客户地址。 
    public void setAddress(String address) { this.address = address; } // 设置客户地址。 
} // 结束客户对象。 
