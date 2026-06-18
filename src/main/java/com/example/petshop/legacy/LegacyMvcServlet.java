package com.example.petshop.legacy; // 声明传统 Servlet 所在包。 
import javax.servlet.ServletException; // 引入 Servlet 异常类型。 
import javax.servlet.http.HttpServlet; // 引入传统 HttpServlet 基类。 
import javax.servlet.http.HttpServletRequest; // 引入 Servlet 请求对象。 
import javax.servlet.http.HttpServletResponse; // 引入 Servlet 响应对象。 
import java.io.IOException; // 引入 IO 异常类型。 
public class LegacyMvcServlet extends HttpServlet { // 定义模拟 Struts 早期前端控制器思想的 Servlet。 
    @Override // 声明重写 GET 请求处理方法。 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // 处理浏览器 GET 请求。 
        response.setContentType("text/html;charset=UTF-8"); // 设置 HTML 响应类型和编码。 
        String action = request.getParameter("action") == null ? "home" : request.getParameter("action"); // 读取动作名称并提供默认首页动作。 
        String html; // 声明即将输出的 HTML 字符串。 
        if ("products".equals(action)) { html = page("商品动作", "Servlet 接管 /legacy/servlet-mvc?action=products，模拟 Struts Action 查询商品。", "/api/products"); } // 功能一：商品动作。 
        else if ("customers".equals(action)) { html = page("客户动作", "Servlet 接管 /legacy/servlet-mvc?action=customers，模拟 Struts Action 管理客户。", "/api/legacy/customers"); } // 功能二：客户动作。 
        else if ("orders".equals(action)) { html = page("订单动作", "Servlet 接管 /legacy/servlet-mvc?action=orders，模拟 Struts Action 查看订单。", "/api/orders"); } // 功能三：订单动作。 
        else if ("stats".equals(action)) { html = page("统计动作", "Servlet 接管 /legacy/servlet-mvc?action=stats，模拟 Struts Action 查看统计。", "/api/legacy/stats"); } // 功能四：统计动作。 
        else { html = page("首页动作", "一个中央 Servlet 接管请求，再根据 action 参数分派五个功能。", "/"); } // 功能五：首页动作。 
        response.getWriter().write(html); // 输出传统 MVC 学习页面。 
    } // 结束 GET 请求处理方法。 
    private String page(String title, String text, String link) { // 定义生成 Servlet 演示页面的工具方法。 
        return "<h1>传统 Servlet/MVC/Struts 思想演示 - " + title + "</h1><p>" + text + "</p><p><a href='" + link + "'>打开对应功能</a></p><p><a href='/legacy/servlet-mvc?action=home'>首页</a> | <a href='/legacy/servlet-mvc?action=products'>商品</a> | <a href='/legacy/servlet-mvc?action=customers'>客户</a> | <a href='/legacy/servlet-mvc?action=orders'>订单</a> | <a href='/legacy/servlet-mvc?action=stats'>统计</a></p><p><a href='/'>返回宠物商店首页</a></p>"; // 返回包含五个动作链接的 HTML。 
    } // 结束页面生成工具方法。 
} // 结束传统 Servlet。 
