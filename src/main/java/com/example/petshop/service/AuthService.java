package com.example.petshop.service; // 声明认证服务所在包。 
import org.springframework.beans.factory.annotation.Value; // 引入读取配置值注解。 
import org.springframework.stereotype.Service; // 引入服务组件注解。 
import javax.crypto.Mac; // 引入 HMAC 签名工具。 
import javax.crypto.spec.SecretKeySpec; // 引入 HMAC 密钥对象。 
import java.nio.charset.StandardCharsets; // 引入 UTF-8 字符集。 
import java.time.Instant; // 引入时间戳类型。 
import java.util.Base64; // 引入 Base64 编码工具。 
@Service // 声明该类是 Spring IoC 管理的认证服务。 
public class AuthService { // 定义简化 JWT 认证服务。 
    @Value("${petshop.jwt-secret}") // 从配置文件读取 JWT 密钥。 
    private String secret; // 保存 JWT 签名密钥。 
    @Value("${petshop.admin-name}") // 从配置文件读取管理员用户名。 
    private String adminName; // 保存管理员用户名。 
    @Value("${petshop.admin-password}") // 从配置文件读取管理员密码。 
    private String adminPassword; // 保存管理员密码。 
    public String login(String username, String password) { // 定义登录并签发令牌方法。 
        if (!adminName.equals(username) || !adminPassword.equals(password)) { throw new IllegalArgumentException("用户名或密码错误"); } // 校验演示管理员账号。 
        String header = encode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}"); // 生成 JWT 头部。 
        String payload = encode("{\"sub\":\"" + username + "\",\"role\":\"ADMIN\",\"exp\":" + Instant.now().plusSeconds(7200).getEpochSecond() + "}"); // 生成 JWT 载荷。 
        return header + "." + payload + "." + sign(header + "." + payload); // 拼接并返回 JWT 字符串。 
    } // 结束登录方法。 
    public boolean verify(String token) { // 定义校验令牌方法。 
        if (token == null || token.split("\\.").length != 3) { return false; } // 基本检查 JWT 三段结构。 
        String[] parts = token.split("\\."); // 拆分 JWT 三段内容。 
        return sign(parts[0] + "." + parts[1]).equals(parts[2]); // 比对签名是否一致。 
    } // 结束校验方法。 
    private String encode(String text) { return Base64.getUrlEncoder().withoutPadding().encodeToString(text.getBytes(StandardCharsets.UTF_8)); } // 使用 URL 安全 Base64 编码 JSON。 
    private String sign(String text) { // 定义 HMAC-SHA256 签名方法。 
        try { // 捕获 Java 加密 API 可能抛出的异常。 
            Mac mac = Mac.getInstance("HmacSHA256"); // 创建 HMAC-SHA256 签名器。 
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256")); // 使用配置密钥初始化签名器。 
            return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(text.getBytes(StandardCharsets.UTF_8))); // 返回 URL 安全签名字符串。 
        } catch (Exception exception) { // 捕获签名异常。 
            throw new IllegalStateException("JWT 签名失败", exception); // 转换为运行时异常。 
        } // 结束异常处理。 
    } // 结束签名方法。 
} // 结束认证服务。 
