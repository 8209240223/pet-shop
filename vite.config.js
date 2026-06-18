import { defineConfig } from "vite"; // 使用 ES6 Module 引入 Vite 配置工具。 
export default defineConfig({ // 使用 ES6 Module 导出 Vite 配置对象。 
    root: "src/main/resources/static", // 指定 Vite 开发服务器根目录。 
    server: { port: 5173, proxy: { "/api": "http://localhost:8080" } }, // 指定 Vite 端口并代理后端 API。 
    build: { outDir: "../../../dist-vite", emptyOutDir: true } // 指定 Vite 构建输出目录。 
}); // 结束 Vite 配置对象。 
