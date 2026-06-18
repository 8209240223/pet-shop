const path = require("path"); // 使用 CommonJS 引入 Node.js 路径模块。 
module.exports = { // 使用 CommonJS 导出 webpack 配置对象。 
    mode: "development", // 指定 webpack 开发模式。 
    entry: "./src/main/resources/static/js/modules/app.js", // 指定 ES6 模块入口文件。 
    output: { path: path.resolve(__dirname, "dist-webpack"), filename: "bundle.js" }, // 指定 webpack 打包输出目录和文件名。 
    experiments: { outputModule: false } // 保持普通浏览器脚本输出方便学习对比。 
}; // 结束 webpack 配置对象。 
