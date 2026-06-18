# 宠物商店学习项目
这个项目按 PDF 的章节顺序把旧技术到新技术放在一个可运行网站里：Servlet/JSP/JDBC/MVC/Struts 思想、Maven、Spring IoC/DI、Spring AOP、Spring MVC、Spring Boot、REST、SOAP/XML、JWT、CORS、JdbcTemplate、MyBatis XML、Hibernate/JPA、MyBatis-Plus、Node/NPM、CommonJS、ES6 Module、webpack、Vite、Vue/React/Angular 依赖入口。
## IntelliJ 运行方式
1. 用 IntelliJ IDEA 打开 `pet-shop-learning` 文件夹。
2. 等待 Maven 自动导入依赖。
3. 运行 `com.example.petshop.PetShopApplication`。
4. 打开 `http://localhost:8080/` 使用宠物商店网站。
## 常用入口
- 首页：`http://localhost:8080/`
- JSP 传统页面：`http://localhost:8080/legacy/jsp-shop`
- Servlet/Struts 思想演示：`http://localhost:8080/legacy/servlet-mvc`
- H2 数据库控制台：`http://localhost:8080/h2-console`
- H2 JDBC URL：`jdbc:h2:file:./data/petshop;MODE=MySQL;DATABASE_TO_UPPER=false`
- 用户名：`sa`
- 密码：空
## 管理员账号
- 用户名：`admin`
- 密码：`123456`
## 后端技术对应
- Maven：`pom.xml`
- Spring Boot：`PetShopApplication`
- Spring IoC/DI：所有 `@Service`、`@Controller`、构造器注入
- Spring AOP：`LoggingAspect`
- Spring MVC：所有 Controller 和 `ShopPageController`
- RESTful API：`/api/products`、`/api/cart`、`/api/orders`
- SOAP/XML：`/api/soap/pet-service`
- JWT：`AuthService` 和 `AuthInterceptor`
- CORS：`WebConfig`
- 原生 JDBC：`LegacyJdbcCustomerDao`
- JdbcTemplate：`LegacyDataController`
- MyBatis XML：`ProductMapper.xml`
- MyBatis-Plus：`ProductMapper extends BaseMapper<Product>`
- Hibernate/JPA：`PetOrder`、`OrderItem`、`OrderRepository`
## 每种技术至少 5 个功能入口
- Servlet / Struts 思想：`/legacy/servlet-mvc?action=home`、`products`、`customers`、`orders`、`stats`
- JSP / JSTL：`/legacy/jsp-shop`、`/legacy/jsp-customers`、`/legacy/jsp-orders`、`/legacy/jsp-tech`、`/legacy/jsp-admin`
- 原生 JDBC：客户列表、新增客户、按电话查询、更新客户、删除客户、客户数量统计
- JdbcTemplate：总统计、分类统计、低库存数量、商品名称列表、订单总额、客户选项
- MyBatis XML：商品列表、关键字搜索、分类列表、分类商品、低库存、价格区间、分类数量
- MyBatis-Plus：推荐商品、商品详情、新增商品、更新商品、删除商品
- Hibernate / JPA：最近订单、按状态查订单、按电话查订单、状态数量、销售总额、订单状态更新
- Spring MVC：REST Controller、JSP Controller、拦截器、Model 数据传递、统一异常处理
- Spring AOP：商品、购物车、订单、认证、技术矩阵这些 Service 调用都会输出切面日志
- RESTful API：商品、购物车、订单、客户、管理员、技术矩阵接口
- SOAP / XML：`getPet`、`listPets`、`featuredPets`、`lowStockPets`、`categories`
- JWT / Interceptor：登录取 Token、新增商品、更新商品、删除商品、Authorization 请求头校验
- ES6 Module：`api.js`、`store.js`、`app.js` 中的商品、购物车、客户、订单、技术实验
- CommonJS / Node / NPM：`price-commonjs.cjs`、`cart-commonjs.cjs`、`package.json`、`webpack.config.cjs`、`npm run webpack`
- webpack / Vite：webpack 入口、webpack 输出、Vite 根目录、Vite API 代理、Vite 构建输出
- Vue / React / Angular：依赖已经写入 `package.json`，用于课堂扩展和三种现代框架对比实验
## 技术覆盖矩阵页面
- JSON 接口：`http://localhost:8080/api/tech/matrix`
- JSP 页面：`http://localhost:8080/legacy/jsp-tech`
- 首页按钮：打开首页后点击“技术覆盖矩阵”
## 前端技术对应
- HTML/CSS/JavaScript/AJAX：`src/main/resources/static`
- ES6 Module：`static/js/modules/*.js`
- CommonJS：`static/js/commonjs/price-commonjs.cjs`
- Node/NPM：`package.json`
- webpack：`webpack.config.cjs`
- Vite：`vite.config.js`
- Vue/React/Angular：作为前端依赖写入 `package.json`，用于课堂扩展和对比实验。
## 说明
源码文件尽量按你的要求逐行写了中文注释；`package.json` 是标准 JSON，语法不允许注释，所以保持无注释以便 NPM 正常识别。
