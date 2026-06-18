import { api } from "./api.js"; // 引入 REST API 模块。
import { store } from "./store.js"; // 引入前端状态仓库模块。

const $ = selector => document.querySelector(selector); // 定义简短 DOM 查询函数。
const money = value => `￥${Number(value || 0).toFixed(2)}`; // 定义金额格式化函数。
const pretty = value => JSON.stringify(value, null, 2); // 定义 JSON 美化输出函数。
const fallbackImage = "/img/product-fallback.svg"; // 定义商品图片加载失败时使用的本地兜底图片。

const escapeMap = { "&": "&amp;", "<": "&lt;", ">": "&gt;", "\"": "&quot;", "'": "&#39;" }; // 定义 HTML 转义字符表。
const esc = value => String(value == null ? "" : value).replace(/[&<>"']/g, ch => escapeMap[ch]); // 定义统一的 HTML 转义函数。

// ===== Toast 通知系统 =====
function showToast(message, type = "info", duration = 3000) {
    const container = $("#toast-container");
    const iconMap = { success: "✅", error: "❌", info: "ℹ️", warning: "⚠️" };
    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `<span class="toast-icon">${iconMap[type] || "ℹ️"}</span><span>${esc(message)}</span><button class="toast-close" aria-label="关闭">×</button>`;
    container.appendChild(toast);
    const close = () => {
        toast.classList.add("toast-out");
        toast.addEventListener("animationend", () => toast.remove(), { once: true });
    };
    toast.querySelector(".toast-close").addEventListener("click", close);
    if (duration > 0) setTimeout(close, duration);
}

// ===== 骨架屏 =====
function skeletonGrid(count = 4) {
    return Array.from({ length: count }, () => `
        <div class="skeleton-card">
            <div class="skeleton skeleton-img"></div>
            <div class="skeleton-body">
                <div class="skeleton skeleton-title"></div>
                <div class="skeleton skeleton-text"></div>
                <div class="skeleton skeleton-text short"></div>
                <div class="skeleton skeleton-price"></div>
                <div class="skeleton skeleton-btn"></div>
            </div>
        </div>`).join("");
}

// ===== 加载状态 =====
function setLoading(element, loading) {
    if (!element) return;
    element.classList.toggle("loading", loading);
    element.disabled = loading;
}

async function loadStats() { // 使用 JdbcTemplate 统计更新首屏。
    const data = await api.stats(); // 请求统计接口。
    $("#stat-products").textContent = data.data.productCount; // 写入商品数量。
    $("#stat-customers").textContent = data.data.customerCount; // 写入客户数量。
    $("#stat-orders").textContent = data.data.orderCount; // 写入订单数量。
    $("#stat-detail").textContent = `${data.data.productCount} 个商品，${data.data.customerCount} 个客户，${data.data.orderCount} 个订单`; // 写入统计文字。
}

async function loadCategories() { // 使用 MyBatis XML 查询分类并渲染按钮。
    const response = await api.categories(); // 请求分类接口。
    $("#category-tabs").innerHTML = response.data.map(category => `<button type="button" data-category="${esc(category)}">${esc(category)}</button>`).join(""); // 渲染分类按钮。
}

async function loadProducts(keyword = "") { // 加载并渲染商品列表。
    $("#product-status").innerHTML = `<span class="spinner"></span> ${keyword ? `正在搜索“${esc(keyword)}”...` : "正在加载商品..."}`;
    $("#product-grid").innerHTML = skeletonGrid(4); // 显示骨架屏。
    try {
        const response = await api.products(keyword); // 请求商品接口。
        renderProducts(response.data, keyword ? `搜索“${esc(keyword)}”` : "全部商品"); // 渲染商品。
        clearActiveCategory(); // 清掉分类按钮的选中态。
    } catch (error) { // 捕获接口失败。
        $("#product-status").textContent = `加载失败：${error.message}`; // 把错误信息显示在状态行。
        $("#product-grid").innerHTML = "";
    }
}

function renderProducts(products, label = "当前筛选") { // 将商品数组渲染到商品网格。
    $("#product-status").textContent = `${label}：共 ${products.length} 个商品`; // 写入商品数量统计。
    if (!products.length) {
        $("#product-grid").innerHTML = `
            <div class="empty-state" style="grid-column: 1 / -1;">
                <div class="empty-state-icon">🔍</div>
                <p>没有找到匹配的商品，可以换一个关键词。</p>
            </div>`;
        return;
    }
    $("#product-grid").innerHTML = products.map(card).join(""); // 渲染商品卡片。
}

function card(product) { // 生成商品卡片 HTML，所有字段都经过 esc 处理。
    const img = esc(product.imageUrl);
    const name = esc(product.name);
    const desc = esc(product.description);
    const category = esc(product.category);
    const fallback = esc(fallbackImage);
    const stock = Number(product.stock) || 0;
    const stockClass = stock <= 3 ? "color:var(--danger);font-weight:700;" : "";
    return `<article class="product-card">
        <img src="${img}" alt="${name}" loading="lazy" onerror="this.onerror=null;this.src='${fallback}'">
        <div class="content">
            <div class="meta">${category} / <span style="${stockClass}">库存 ${stock}</span></div>
            <h3>${name}</h3>
            <p>${desc}</p>
            <div class="price">${money(product.price)}</div>
            <button type="button" data-add="${esc(product.id)}">加入购物车</button>
        </div>
    </article>`;
}

async function loadCart() { // 加载并渲染购物车。
    const cart = await api.cart(); // 请求购物车接口。
    if (!cart.items.length) {
        $("#cart-list").innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🛒</div>
                <p>购物车还是空的，快去选购心仪商品吧。</p>
            </div>`;
    } else {
        $("#cart-list").innerHTML = cart.items.map(line).join("");
    }
    $("#cart-total").textContent = `合计：${money(cart.total)}`; // 写入合计金额。
}

function line(item) { // 生成购物车行 HTML。
    const productName = esc(item.product.name);
    return `<div class="line-item">
        <span>${productName} × ${Number(item.quantity) || 0}</span>
        <strong>${money(item.subtotal)}</strong>
        <button type="button" class="secondary small" data-remove="${esc(item.product.id)}">移除</button>
    </div>`;
}

async function loadCustomers() { // 使用原生 JDBC 查询并渲染客户列表。
    const response = await api.customers(); // 请求客户接口。
    if (!response.data.length) {
        $("#customer-list").innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">👤</div>
                <p>暂无客户。</p>
            </div>`;
    } else {
        $("#customer-list").innerHTML = response.data.map(customerLine).join("");
    }
}

function customerLine(customer) { // 生成客户行 HTML。
    return `<div class="line-item"><span>${esc(customer.name)} / ${esc(customer.phone)}</span><strong>${esc(customer.email)}</strong></div>`;
}

async function loadOrders() { // 使用 JPA 查询并渲染订单列表。
    const response = await api.orders(); // 请求订单接口。
    if (!response.data.length) {
        $("#order-list").innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">📦</div>
                <p>暂无订单。</p>
            </div>`;
    } else {
        $("#order-list").innerHTML = response.data.map(orderLine).join("");
    }
}

function orderLine(order) { // 生成订单行 HTML。
    const statusColor = order.status === "已创建" ? "var(--success)" : (order.status === "已取消" ? "var(--danger)" : "var(--tab)");
    return `<div class="line-item">
        <span>订单 ${esc(order.id)} / ${esc(order.customerName)} / <span style="color:${statusColor};font-weight:600;">${esc(order.status)}</span></span>
        <strong>${money(order.totalAmount)}</strong>
    </div>`;
}

function setActiveCategory(category) { // 给分类按钮加上选中态。
    $("#category-tabs").querySelectorAll("button").forEach(btn => btn.classList.toggle("is-active", btn.dataset.category === category));
}

function clearActiveCategory() { // 清除分类按钮的选中态。
    $("#category-tabs").querySelectorAll("button").forEach(btn => btn.classList.remove("is-active"));
}

// ===== 事件绑定 =====
$("#search-form").addEventListener("submit", event => { // 绑定商品搜索事件。
    event.preventDefault(); // 阻止表单默认刷新。
    loadProducts($("#keyword").value).catch(err => showTechError(err)); // 触发搜索。
});

$("#category-tabs").addEventListener("click", event => { // 绑定分类查询事件。
    const category = event.target.dataset.category; // 读取被点击按钮的分类。
    if (!category) return; // 点空白处直接忽略。
    setActiveCategory(category); // 高亮当前分类。
    $("#product-status").innerHTML = `<span class="spinner"></span> 正在加载“${esc(category)}”分类...`; // 写入加载中文案。
    $("#product-grid").innerHTML = skeletonGrid(4); // 骨架屏。
    api.productsByCategory(category)
        .then(response => renderProducts(response.data, `分类“${esc(category)}”`))
        .catch(error => { $("#product-status").textContent = `加载失败：${error.message}`; $("#product-grid").innerHTML = ""; });
});

$("#featured-products").addEventListener("click", () => {
    const btn = $("#featured-products");
    setLoading(btn, true);
    api.featuredProducts().then(response => renderProducts(response.data, "推荐商品")).then(clearActiveCategory).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});
$("#low-stock-products").addEventListener("click", () => {
    const btn = $("#low-stock-products");
    setLoading(btn, true);
    api.lowStockProducts().then(response => renderProducts(response.data, "低库存商品")).then(clearActiveCategory).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});
$("#cheap-products").addEventListener("click", () => {
    const btn = $("#cheap-products");
    setLoading(btn, true);
    api.cheapProducts().then(response => renderProducts(response.data, "500 元以内商品")).then(clearActiveCategory).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});

$("#product-grid").addEventListener("click", event => { // 绑定加入购物车事件。
    const id = event.target.dataset.add; // 读取按钮上的商品 ID。
    if (!id) return; // 点空白处直接忽略。
    const button = event.target; // 缓存按钮元素。
    const original = button.textContent; // 暂存原始文案。
    setLoading(button, true); // 防止重复点击并显示加载。
    api.addCart(id)
        .then(() => loadCart())
        .then(() => { showToast("已加入购物车", "success", 2000); })
        .then(() => { button.textContent = "已加入"; button.classList.remove("loading"); button.disabled = false; setTimeout(() => { button.textContent = original; }, 1200); })
        .catch(error => { button.textContent = original; button.disabled = false; button.classList.remove("loading"); showToast(error.message, "error"); });
});

$("#cart-list").addEventListener("click", event => { // 绑定移除购物车事件。
    const id = event.target.dataset.remove; // 读取要移除的商品 ID。
    if (!id) return;
    api.removeCart(id).then(() => loadCart()).then(() => showToast("已从购物车移除", "info", 2000)).catch(err => showToast(err.message, "error"));
});

$("#clear-cart").addEventListener("click", () => {
    const btn = $("#clear-cart");
    setLoading(btn, true);
    api.clearCart().then(() => loadCart()).then(() => showToast("购物车已清空", "info", 2000)).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showToast(err.message, "error"); });
});

$("#checkout-form").addEventListener("submit", event => { // 绑定下单事件。
    event.preventDefault(); // 阻止表单默认刷新。
    const name = $("#customer-name").value.trim(); // 读取联系人姓名。
    const phone = $("#customer-phone").value.trim(); // 读取联系人电话。
    const btn = event.target.querySelector("button[type='submit']");
    if (!name || !phone) { $("#checkout-message").textContent = "请填写姓名和电话"; return; } // 简单的前端校验。
    setLoading(btn, true);
    api.checkout(name, phone)
        .then(data => { $("#checkout-message").textContent = `订单 ${data.data.id} 已创建`; showToast(`订单 ${data.data.id} 创建成功！`, "success", 4000); return Promise.allSettled([loadCart(), loadProducts(), loadOrders(), loadStats()]); })
        .then(() => setLoading(btn, false))
        .catch(error => { $("#checkout-message").textContent = error.message; setLoading(btn, false); showToast(error.message, "error"); });
});

$("#reload-customers").addEventListener("click", () => {
    const btn = $("#reload-customers");
    setLoading(btn, true);
    loadCustomers().then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});

$("#customer-form").addEventListener("submit", event => { // 绑定新增客户事件。
    event.preventDefault(); // 阻止默认刷新。
    const customer = readCustomerForm(); // 读取表单。
    const btn = event.target.querySelector("button[type='submit']");
    setLoading(btn, true);
    api.createCustomer(customer)
        .then(() => { $("#customer-message").textContent = "客户新增成功"; showToast("客户新增成功", "success", 3000); return Promise.allSettled([loadCustomers(), loadStats()]); })
        .then(() => setLoading(btn, false))
        .catch(error => { $("#customer-message").textContent = error.message; setLoading(btn, false); showToast(error.message, "error"); });
});

$("#reload-orders").addEventListener("click", () => {
    const btn = $("#reload-orders");
    setLoading(btn, true);
    loadOrders().then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});
$("#created-count").addEventListener("click", () => api.createdOrderCount().then(response => $("#order-output").textContent = `已创建订单数量：${response.data}`).catch(showTechError)); // 绑定订单状态统计事件。
$("#sales-total").addEventListener("click", () => api.salesTotal().then(response => $("#order-output").textContent = `销售总额：${money(response.data)}`).catch(showTechError)); // 绑定销售额统计事件。

$("#login-form").addEventListener("submit", event => { // 绑定管理员登录事件。
    event.preventDefault(); // 阻止默认刷新。
    const btn = event.target.querySelector("button[type='submit']");
    setLoading(btn, true);
    api.login($("#admin-name").value, $("#admin-password").value)
        .then(data => { store.setToken(data.token); $("#login-message").textContent = "JWT 已保存，可以新增商品"; showToast("管理员登录成功", "success", 3000); })
        .catch(error => { $("#login-message").textContent = error.message; showToast(error.message, "error"); })
        .finally(() => setLoading(btn, false));
});

$("#product-form").addEventListener("submit", event => { // 绑定新增商品事件。
    event.preventDefault(); // 阻止默认刷新。
    const product = readProductForm(); // 读取表单。
    const btn = event.target.querySelector("button[type='submit']");
    setLoading(btn, true);
    api.createProduct(store.getToken(), product)
        .then(() => { $("#product-message").textContent = "商品新增成功"; showToast("商品新增成功", "success", 3000); return Promise.allSettled([loadProducts(), loadCategories(), loadStats()]); })
        .then(() => setLoading(btn, false))
        .catch(error => { $("#product-message").textContent = error.message; setLoading(btn, false); showToast(error.message, "error"); });
});

$("#soap-demo").addEventListener("click", () => {
    const btn = $("#soap-demo");
    setLoading(btn, true);
    api.soap().then(xml => $("#tech-output").textContent = xml).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});
$("#matrix-demo").addEventListener("click", () => {
    const btn = $("#matrix-demo");
    setLoading(btn, true);
    api.matrix().then(response => $("#tech-output").textContent = pretty(response.data)).then(() => setLoading(btn, false)).catch(err => { setLoading(btn, false); showTechError(err); });
});

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
        showToast("部分模块加载失败，请检查技术输出区", "warning", 5000);
    } else {
        showToast("所有模块加载成功，欢迎使用！", "success", 3000);
    }
});
