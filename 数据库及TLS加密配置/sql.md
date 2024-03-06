1.user

| 字段名      | 数据类型    | 说明     | 备注                |
| ----------- | ----------- | -------- | ------------------- |
| id          | bigint      | 主键     | 自增                |
| username    | varchar(32) | 用户名   | 唯一，md5加密       |
| email       | varchar(64) | 邮箱     |                     |
| password    | varchar(64) | 密码     |                     |
| create_time | datetime    | 创建时间 |                     |
| permission  | bigint      | 用户权限 | 1 管理员 0 普通用户 |

2.image

| 字段名    | 数据类型     | 说明         | 备注 |
| :-------- | ------------ | ------------ | ---- |
| image_id  | varchar(60)  | 镜像id       | 唯一 |
| name      | varchar(50)  | 镜像名       |      |
| image     | varchar(255) | 镜像图片     |      |
| tag       | varchar(20)  | 镜像标签     |      |
| size      | varcahr(10)  | 镜像大小     |      |
| is_active | boolean      | 镜像是否启用 |      |

3.container

| 字段名           | 数据类型     | 说明             | 备注                  |
| :--------------- | ------------ | ---------------- | --------------------- |
| container_id     | varchar(60)  | 容器id           | 唯一                  |
| host_port        | bigint       | 主机端口映射     | 用于VNC服务访问，非空 |
| image_id         | varchar(12)  | 镜像id           | 逻辑外键              |
| name             | varcahr(30)  | 容器名           |                       |
| image            | varchar(255) | 容器图片         |                       |
| create_time      | datetime     | 容器创建时间     |                       |
| user_id          | bigint       | 创建人id         | 逻辑外键              |
| last_access_time | datetime     | 容器最后访问时间 |                       |
| status           | int          | 容器状态         | 1 启动 0 关闭 -1 销毁 |

