import { api } from "./api.js"; // 引入 REST API 模块。
import { store } from "./store.js"; // 引入前端状态仓库模块。

const $ = selector => document.querySelector(selector); // 定义简短 DOM 查询函数。
const money = value => `￥${Number(value || 0).toFixed(2)}`; // 定义金额格式化函数。
const pretty = value => JSON.stringify(value, null, 2); // 定义 JSON 美化输出函数。
const fallbackImage = "/img/product-fallback.svg"; // 定义商品图片加载失败时使用的本地兜底图片。

const escapeMap = { "&": "&amp;", "<": "&lt;", ">": "&gt;", "\"": "&quot;", "'": "&#39;" }; // 定义 HTML 转义字符表，避免后端字段被解释成标签。
const esc = value => String(value == null ? "" : value).replace(/[&<>"']/g, ch => escapeMap[ch]); // 定义统一的 HTML 转义函数，所有动态字段渲染前都要先经过这里。

async function loadStats() { // 使用 JdbcTemplate 统计更新首屏。
    const data = await api.stats(); // 请求统计接口。
    $("#stat-products").textContent = data.data.productCount; // 写入商品数量。
    $("#stat-customers").textContent = data.data.customerCount; // 写入客户数量。
    $("#stat-orders").textContent = data.data.orderCount; // 写入订单数量。
    $("#stat-detail").textContent = `${data.data.productCount} 个商品，${data.data.customerCount} 个客户，${data.data.orderCount} 个订单`; // 写入统计文字。
}

async function loadCategories() { // 使用 MyBatis XML 查询分类并渲染按钮。
    const response = await api.categories(); // 请求分类接口。
    $("#category-tabs").innerHTML = response.data.map(category => `<button type="button" data-category="${esc(category)}">${esc(category)}</button>`).join(""); // 渲染分类按钮，分类名转义后写入。
}

async function loadProducts(keyword = "") { // 加载并渲染商品列表。
    $("#product-status").textContent = keyword ? `正在搜索“${keyword}”...` : "正在加载全部商品..."; // 写入加载中文案。
    try {
        const response = await api.products(keyword); // 请求商品接口。
        renderProducts(response.data, keyword ? `搜索“${keyword}”` : "全部商品"); // 渲染商品。
        clearActiveCategory(); // 清掉分类按钮的选中态，回归到搜索/全部场景。
    } catch (error) { // 捕获接口失败。
        $("#product-status").textContent = `加载失败：${error.message}`; // 把错误信息显示在状态行。
    }
}

function renderProducts(products, label = "当前筛选") { // 将商品数组渲染到商品网格。
    $("#product-status").textContent = `${label}：共 ${products.length} 个商品`; // 写入商品数量统计。
    $("#product-grid").innerHTML = products.length ? products.map(card).join("") : "<p class='meta'>没有找到匹配的商品，可以换一个关键词。</p>"; // 渲染商品卡片或空状态。
}

function card(product) { // 生成商品卡片 HTML，所有字段都经过 esc 处理。
    const img = esc(product.imageUrl); // 转义图片地址。
    const name = esc(product.name); // 转义商品名称。
    const desc = esc(product.description); // 转义商品描述。
    const category = esc(product.category); // 转义商品分类。
    const fallback = esc(fallbackImage); // 转义兜底图片地址。
    return `<article class="product-card"><img src="${img}" alt="${name}" loading="lazy" onerror="this.onerror=null;this.src='${fallback}'"><div class="content"><div class="meta">${category} / 库存 ${Number(product.stock) || 0}</div><h3>${name}</h3><p>${desc}</p><div class="price">${money(product.price)}</div><button type="button" data-add="${esc(product.id)}">加入购物车</button></div></article>`;
}

async function loadCart() { // 加载并渲染购物车。
    const cart = await api.cart(); // 请求购物车接口。
    $("#cart-list").innerHTML = cart.items.length ? cart.items.map(line).join("") : "<p class='meta'>购物车还是空的。</p>"; // 渲染购物车行或空状态。
    $("#cart-total").textContent = `合计：${money(cart.total)}`; // 写入合计金额。
}

function line(item) { // 生成购物车行 HTML。
    const productName = esc(item.product.name); // 转义商品名称。
    return `<div class="line-item"><span>${productName} × ${Number(item.quantity) || 0}</span><strong>${money(item.subtotal)}</strong><button type="button" class="secondary" data-remove="${esc(item.product.id)}">移除</button></div>`;
}

async function loadCustomers() { // 使用原生 JDBC 查询并渲染客户列表。
    const response = await api.customers(); // 请求客户接口。
    $("#customer-list").innerHTML = response.data.length ? response.data.map(customerLine).join("") : "<p class='meta'>暂无客户。</p>"; // 渲染客户行或空状态。
}

function customerLine(customer) { // 生成客户行 HTML。
    return `<div class="line-item"><span>${esc(customer.name)} / ${esc(customer.phone)}</span><strong>${esc(customer.email)}</strong></div>`;
}

async function loadOrders() { // 使用 JPA 查询并渲染订单列表。
    const response = await api.orders(); // 请求订单接口。
    $("#order-list").innerHTML = response.data.length ? response.data.map(orderLine).join("") : "<p class='meta'>暂无订单。</p>"; // 渲染订单行或空状态。
}

function orderLine(order) { // 生成订单行 HTML。
    return `<div class="line-item"><span>订单 ${esc(order.id)} / ${esc(order.customerName)} / ${esc(order.status)}</span><strong>${money(order.totalAmount)}</strong></div>`;
}

function setActiveCategory(category) { // 给分类按钮加上选中态，便于用户看出当前过滤条件。
    $("#category-tabs").querySelectorAll("button").forEach(btn => btn.classList.toggle("is-active", btn.dataset.category === category));
}

function clearActiveCategory() { // 清除分类按钮的选中态。
    $("#category-tabs").querySelectorAll("button").forEach(btn => btn.classList.remove("is-active"));
}

$("#search-form").addEventListener("submit", event => { // 绑定商品搜索事件。
    event.preventDefault(); // 阻止表单默认刷新。
    loadProducts($("#keyword").value).catch(showTechError); // 触发搜索。
});

$("#category-tabs").addEventListener("click", event => { // 绑定分类查询事件。
    const category = event.target.dataset.category; // 读取被点击按钮的分类。
    if (!category) return; // 点空白处直接忽略。
    setActiveCategory(category); // 高亮当前分类。
    $("#product-status").textContent = `正在加载“${category}”分类...`; // 写入加载中文案。
    api.productsByCategory(category)
        .then(response => renderProducts(response.data, `分类“${category}”`))
        .catch(error => $("#product-status").textContent = `加载失败：${error.message}`);
});

$("#featured-products").addEventListener("click", () => api.featuredProducts().then(response => renderProducts(response.data, "推荐商品")).then(clearActiveCategory).catch(showTechError)); // 绑定推荐商品事件。
$("#low-stock-products").addEventListener("click", () => api.lowStockProducts().then(response => renderProducts(response.data, "低库存商品")).then(clearActiveCategory).catch(showTechError)); // 绑定低库存商品事件。
$("#cheap-products").addEventListener("click", () => api.cheapProducts().then(response => renderProducts(response.data, "500 元以内商品")).then(clearActiveCategory).catch(showTechError)); // 绑定低价商品事件。

$("#product-grid").addEventListener("click", event => { // 绑定加入购物车事件。
    const id = event.target.dataset.add; // 读取按钮上的商品 ID。
    if (!id) return; // 点空白处直接忽略。
    const button = event.target; // 缓存按钮元素。
    const original = button.textContent; // 暂存原始文案。
    button.disabled = true; // 防止重复点击。
    button.textContent = "加入中..."; // 显示进行中文案。
    api.addCart(id)
        .then(loadCart)
        .then(() => { button.textContent = "已加入"; setTimeout(() => { button.textContent = original; button.disabled = false; }, 900); }) // 成功后短暂展示反馈。
        .catch(error => { button.textContent = original; button.disabled = false; showTechError(error); }); // 失败时恢复按钮并提示。
});

$("#cart-list").addEventListener("click", event => { // 绑定移除购物车事件。
    const id = event.target.dataset.remove; // 读取要移除的商品 ID。
    if (id) api.removeCart(id).then(loadCart).catch(showTechError); // 触发移除。
});

$("#clear-cart").addEventListener("click", () => api.clearCart().then(loadCart).catch(showTechError)); // 绑定清空购物车事件。

$("#checkout-form").addEventListener("submit", event => { // 绑定下单事件。
    event.preventDefault(); // 阻止表单默认刷新。
    const name = $("#customer-name").value.trim(); // 读取联系人姓名。
    const phone = $("#customer-phone").value.trim(); // 读取联系人电话。
    if (!name || !phone) { $("#checkout-message").textContent = "请填写姓名和电话"; return; } // 简单的前端校验。
    api.checkout(name, phone)
        .then(data => { $("#checkout-message").textContent = `订单 ${data.data.id} 已创建`; return Promise.allSettled([loadCart(), loadProducts(), loadOrders(), loadStats()]); })
        .catch(error => $("#checkout-message").textContent = error.message);
});

$("#reload-customers").addEventListener("click", () => loadCustomers().catch(showTechError)); // 绑定刷新客户事件。

$("#customer-form").addEventListener("submit", event => { // 绑定新增客户事件。
    event.preventDefault(); // 阻止默认刷新。
    const customer = readCustomerForm(); // 读取表单。
    api.createCustomer(customer)
        .then(() => { $("#customer-message").textContent = "客户新增成功"; return Promise.allSettled([loadCustomers(), loadStats()]); })
        .catch(error => $("#customer-message").textContent = error.message);
});

$("#reload-orders").addEventListener("click", () => loadOrders().catch(showTechError)); // 绑定刷新订单事件。
$("#created-count").addEventListener("click", () => api.createdOrderCount().then(response => $("#order-output").textContent = `已创建订单数量：${response.data}`).catch(showTechError)); // 绑定订单状态统计事件。
$("#sales-total").addEventListener("click", () => api.salesTotal().then(response => $("#order-output").textContent = `销售总额：${money(response.data)}`).catch(showTechError)); // 绑定销售额统计事件。

$("#login-form").addEventListener("submit", event => { // 绑定管理员登录事件。
    event.preventDefault(); // 阻止默认刷新。
    api.login($("#admin-name").value, $("#admin-password").value)
        .then(data => { store.setToken(data.token); $("#login-message").textContent = "JWT 已保存，可以新增商品"; })
        .catch(error => $("#login-message").textContent = error.message);
});

$("#product-form").addEventListener("submit", event => { // 绑定新增商品事件。
    event.preventDefault(); // 阻止默认刷新。
    const product = readProductForm(); // 读取表单。
    api.createProduct(store.getToken(), product)
        .then(() => { $("#product-message").textContent = "商品新增成功"; return Promise.allSettled([loadProducts(), loadCategories(), loadStats()]); })
        .catch(error => $("#product-message").textContent = error.message);
});

$("#soap-demo").addEventListener("click", () => api.soap().then(xml => $("#tech-output").textContent = xml).catch(showTechError)); // 绑定 SOAP XML 演示事件。
$("#matrix-demo").addEventListener("click", () => api.matrix().then(response => $("#tech-output").textContent = pretty(response.data)).catch(showTechError)); // 绑定技术覆盖矩阵事件。

function readProductForm() { // 从表单读取新商品对象。
    return { name: $("#new-name").value, category: $("#new-category").value, price: $("#new-price").value, stock: Number($("#new-stock").value), imageUrl: $("#new-image").value, description: $("#new-desc").value, featured: false };
}

function readCustomerForm() { // 从表单读取新客户对象。
    return { name: $("#member-name").value, phone: $("#member-phone").value, email: $("#member-email").value, address: $("#member-address").value };
}

function showTechError(error) { // 将错误展示到技术输出区。
    $("#tech-output").textContent = error && error.message ? error.message : "请求失败";
}

const bootTasks = [ // 启动时要并行加载的任务列表。
    ["统计", loadStats, "#stat-detail"],
    ["分类", loadCategories, "#product-status"],
    ["商品", loadProducts, "#product-status"],
    ["购物车", loadCart, "#cart-total"],
    ["客户", loadCustomers, "#customer-list"],
    ["订单", loadOrders, "#order-list"],
];

Promise.allSettled(bootTasks.map(([, fn]) => fn())).then(results => { // 用 allSettled 保证单个接口失败不影响其他模块渲染。
    const failed = results
        .map((result, index) => ({ result, label: bootTasks[index][0] }))
        .filter(entry => entry.result.status === "rejected"); // 收集失败的模块。
    if (failed.length) { // 有失败模块时统一在技术输出区提示。
        const messages = failed.map(entry => `${entry.label}：${entry.result.reason && entry.result.reason.message ? entry.result.reason.message : "请求失败"}`).join("\n");
        $("#tech-output").textContent = `部分模块加载失败，可单独重试。\n${messages}`;
    }
});
