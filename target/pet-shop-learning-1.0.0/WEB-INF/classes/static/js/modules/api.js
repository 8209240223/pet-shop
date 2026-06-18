const json = response => response.json().then(data => { if (!response.ok || data.success === false) { throw new Error(data.message || "请求失败"); } return data; }); // 定义统一 JSON 响应处理函数。 
export const api = { // 导出 REST API 调用对象。 
    products: keyword => fetch(`/api/products${keyword ? `?keyword=${encodeURIComponent(keyword)}` : ""}`).then(json), // 查询商品列表。 
    featuredProducts: () => fetch("/api/products/featured").then(json), // 查询推荐商品列表。 
    categories: () => fetch("/api/products/categories").then(json), // 查询商品分类列表。 
    productsByCategory: category => fetch(`/api/products/category/${encodeURIComponent(category)}`).then(json), // 按分类查询商品。 
    lowStockProducts: () => fetch("/api/products/low-stock?limit=5").then(json), // 查询低库存商品。 
    cheapProducts: () => fetch("/api/products/price-range?min=1&max=500").then(json), // 查询低价商品。 
    cart: () => fetch("/api/cart").then(json), // 查询当前购物车。 
    addCart: id => fetch(`/api/cart/${id}`, { method: "POST" }).then(json), // 将商品加入购物车。 
    removeCart: id => fetch(`/api/cart/${id}`, { method: "DELETE" }).then(json), // 从购物车移除商品。 
    clearCart: () => fetch("/api/cart", { method: "DELETE" }).then(json), // 清空购物车。 
    checkout: (name, phone) => fetch(`/api/orders?customerName=${encodeURIComponent(name)}&phone=${encodeURIComponent(phone)}`, { method: "POST" }).then(json), // 提交订单。 
    orders: () => fetch("/api/orders").then(json), // 查询最近订单。 
    createdOrderCount: () => fetch("/api/orders/status/已创建/count").then(json), // 统计已创建订单数量。 
    salesTotal: () => fetch("/api/orders/sales-total").then(json), // 统计订单销售额。 
    customers: () => fetch("/api/legacy/customers").then(json), // 使用原生 JDBC 查询客户列表。 
    createCustomer: customer => fetch(`/api/legacy/customers?name=${encodeURIComponent(customer.name)}&phone=${encodeURIComponent(customer.phone)}&email=${encodeURIComponent(customer.email)}&address=${encodeURIComponent(customer.address)}`, { method: "POST" }).then(json), // 使用原生 JDBC 新增客户。 
    login: (username, password) => fetch(`/api/auth/login?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, { method: "POST" }).then(json), // 管理员登录。 
    createProduct: (token, product) => fetch("/api/admin/products", { method: "POST", headers: { "Content-Type": "application/json", "Authorization": `Bearer ${token}` }, body: JSON.stringify(product) }).then(json), // 管理员新增商品。 
    stats: () => fetch("/api/legacy/stats").then(json), // 查询 JdbcTemplate 统计。 
    matrix: () => fetch("/api/tech/matrix").then(json), // 查询技术覆盖矩阵。 
    soap: () => fetch("/api/soap/pet-service", { method: "POST", headers: { "Content-Type": "text/xml" }, body: "<soap:Envelope><soap:Body><listPets></listPets></soap:Body></soap:Envelope>" }).then(response => response.text()) // 调用 SOAP 风格 XML 接口。 
}; // 结束 REST API 调用对象。 
