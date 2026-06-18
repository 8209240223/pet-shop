MERGE INTO products KEY(id) VALUES (1, '英短蓝猫幼猫', '猫咪', 1688.00, 5, '性格温顺，已完成基础体检，适合第一次养猫的家庭。', 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=900&q=80', TRUE, CURRENT_TIMESTAMP); -- 初始化猫咪商品。 
MERGE INTO products KEY(id) VALUES (2, '柯基幼犬', '狗狗', 2588.00, 3, '活泼亲人，附带疫苗记录和新手喂养指南。', 'https://images.unsplash.com/photo-1557973557-ddfa9ee8c4a9?auto=format&fit=crop&w=900&q=80', TRUE, CURRENT_TIMESTAMP); -- 初始化狗狗商品。 
MERGE INTO products KEY(id) VALUES (3, '仓鼠豪华笼套装', '用品', 199.00, 20, '包含跑轮、饮水器、木屑和隐藏小屋。', 'https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=900&q=80', FALSE, CURRENT_TIMESTAMP); -- 初始化宠物用品商品。 
MERGE INTO products KEY(id) VALUES (4, '鹦鹉营养粮', '粮食', 59.00, 50, '混合谷物与维生素颗粒，适合中小型鹦鹉。', 'https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=900&q=80', FALSE, CURRENT_TIMESTAMP); -- 初始化宠物粮食商品。 
MERGE INTO customers KEY(id) VALUES (1, '张同学', '13800000001', 'student@example.com', '校内宿舍 3 栋 501'); -- 初始化客户示例数据。 
MERGE INTO customers KEY(id) VALUES (2, '李老师', '13800000002', 'teacher@example.com', '教师公寓 2 单元 301'); -- 初始化客户示例数据。 
