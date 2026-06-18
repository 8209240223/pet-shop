<%@ page contentType="text/html;charset=UTF-8" language="java" %> <!-- 声明 JSP 页面编码和语言。 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 引入 JSTL 核心标签库。 -->
<!DOCTYPE html> <!-- 声明 HTML5 文档。 -->
<html lang="zh-CN"> <!-- 设置页面语言为中文。 -->
<head> <!-- 开始 JSP 页面头部。 -->
    <meta charset="UTF-8"> <!-- 设置页面字符编码。 -->
    <title>${title}</title> <!-- 输出页面标题。 -->
    <link rel="stylesheet" href="/css/style.css"> <!-- 复用主站 CSS 样式。 -->
</head> <!-- 结束 JSP 页面头部。 -->
<body> <!-- 开始 JSP 页面主体。 -->
<main class="layout"> <!-- 定义 JSP 页面内容容器。 -->
    <section class="workspace"> <!-- 定义 JSP 订单区域。 -->
        <div class="section-title"> <!-- 定义标题行。 -->
            <h1>${title}</h1> <!-- 输出订单页面标题。 -->
            <a href="/">返回首页</a> <!-- 返回现代前端首页。 -->
        </div> <!-- 结束标题行。 -->
        <div class="list-box"> <!-- 定义订单列表容器。 -->
            <c:forEach items="${orders}" var="order"> <!-- 使用 JSTL 遍历订单集合。 -->
                <div class="line-item"><span>订单 ${order.id} / ${order.customerName} / ${order.status}</span><strong>￥${order.totalAmount}</strong></div> <!-- 输出订单行。 -->
            </c:forEach> <!-- 结束订单集合遍历。 -->
        </div> <!-- 结束订单列表容器。 -->
    </section> <!-- 结束 JSP 订单区域。 -->
</main> <!-- 结束 JSP 页面内容容器。 -->
</body> <!-- 结束 JSP 页面主体。 -->
</html> <!-- 结束 JSP 页面。 -->
