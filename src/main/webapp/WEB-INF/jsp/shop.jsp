<%@ page contentType="text/html;charset=UTF-8" language="java" %> <!-- 声明 JSP 页面编码和语言。 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 引入 JSTL 核心标签库。 -->
<!DOCTYPE html> <!-- 声明 HTML5 文档。 -->
<html lang="zh-CN"> <!-- 设置页面语言为中文。 -->
<head> <!-- 开始 JSP 页面头部。 -->
    <meta charset="UTF-8"> <!-- 设置页面字符编码。 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- 适配移动端。 -->
    <title>${title}</title> <!-- 使用 Spring MVC Model 中的标题。 -->
    <link rel="stylesheet" href="/css/style.css"> <!-- 复用主站 CSS 样式。 -->
</head> <!-- 结束 JSP 页面头部。 -->
<body> <!-- 开始 JSP 页面主体。 -->
<header class="topbar">
    <div class="brand"><span class="brand-icon">🐾</span>宠物商店学习站</div>
    <nav class="nav" aria-label="主导航">
        <a href="/">现代首页</a>
        <a href="/legacy/jsp-shop">商品</a>
        <a href="/legacy/jsp-customers">客户</a>
        <a href="/legacy/jsp-orders">订单</a>
        <a href="/legacy/jsp-admin">管理</a>
        <a href="/legacy/jsp-tech">技术矩阵</a>
    </nav>
</header>
<main class="layout"> <!-- 定义 JSP 页面内容容器。 -->
    <section class="workspace"> <!-- 定义 JSP 商品区域。 -->
        <div class="section-title"> <!-- 定义标题行。 -->
            <h1>${title}</h1> <!-- 输出 JSP 页面标题。 -->
            <a href="/">返回前后端分离首页</a> <!-- 返回现代前端首页。 -->
        </div> <!-- 结束标题行。 -->
        <div class="product-grid"> <!-- 定义 JSP 商品网格。 -->
            <c:forEach items="${products}" var="product"> <!-- 使用 JSTL 遍历商品集合。 -->
                <article class="product-card"> <!-- 定义 JSP 商品卡片。 -->
                    <img src="${product.imageUrl}" alt="${product.name}"> <!-- 输出商品图片。 -->
                    <div class="content"> <!-- 定义商品内容容器。 -->
                        <div class="meta">${product.category} / 库存 ${product.stock}</div> <!-- 输出商品分类和库存。 -->
                        <h3>${product.name}</h3> <!-- 输出商品名称。 -->
                        <p>${product.description}</p> <!-- 输出商品描述。 -->
                        <div class="price">￥${product.price}</div> <!-- 输出商品价格。 -->
                    </div> <!-- 结束商品内容容器。 -->
                </article> <!-- 结束 JSP 商品卡片。 -->
            </c:forEach> <!-- 结束商品集合遍历。 -->
        </div> <!-- 结束 JSP 商品网格。 -->
    </section> <!-- 结束 JSP 商品区域。 -->
</main> <!-- 结束 JSP 页面内容容器。 -->
</body> <!-- 结束 JSP 页面主体。 -->
</html> <!-- 结束 JSP 页面。 -->
