let token = ""; // 保存管理员 JWT Token。 
export const store = { // 导出前端状态仓库。 
    setToken(value) { token = value; }, // 保存登录后返回的 Token。 
    getToken() { return token; } // 读取当前 Token。 
}; // 结束前端状态仓库。 
