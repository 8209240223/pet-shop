const countItems = items => items.reduce((sum, item) => sum + item.quantity, 0); // 定义 CommonJS 购物车商品件数统计函数。 
const sumItems = items => items.reduce((sum, item) => sum + item.subtotal, 0); // 定义 CommonJS 购物车金额汇总函数。 
const hasItems = items => items.length > 0; // 定义 CommonJS 判断购物车是否有商品函数。 
const firstItemName = items => hasItems(items) ? items[0].product.name : "空购物车"; // 定义 CommonJS 获取首个商品名称函数。 
const describeCart = items => `购物车共有 ${countItems(items)} 件商品`; // 定义 CommonJS 购物车文字描述函数。 
module.exports = { countItems, sumItems, hasItems, firstItemName, describeCart }; // 使用 CommonJS 导出五个购物车工具函数。 
