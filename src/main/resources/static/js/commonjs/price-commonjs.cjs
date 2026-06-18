const formatPrice = value => `￥${Number(value || 0).toFixed(2)}`; // 定义 CommonJS 价格格式化函数。 
module.exports = { formatPrice }; // 使用 CommonJS 规范导出函数。 
