# 数据库表设计
## 用户表 - user
1.user

| 字段名      | 数据类型    | 说明   | 备注        |
| ----------- | ----------- | ------ |-----------|
| id          | bigint      | 主键   | 自增        |
| username    | varchar(32) | 用户名 | 唯一        |
| email       | varchar(64) | 邮箱   |           |
| password    | varchar(64) | 密码   | 哈希值 md5加密 |
| phone       | varchar(11) | 手机号 |           |
| create_time | datetime    | 创建时间 |           |
| delete_time | datetime    | 删除时间 |           |

### `id`
- 主键
作用：唯一标识用户

### `username`

- 用户名
- 类型：varchar(32)
- 唯一
作用：用户登录时的用户名

### `email`

- 邮箱
- 类型：varchar(64)
- 唯一
作用：用户注册时的邮箱

### `password`

- 密码
- 类型：varchar(64)
- 哈希值，可以使用md5加密，不建议使用明文
作用：用户登录时的密码

### `phone`

- 手机号
- 类型：varchar(11)
- 可选，唯一
作用：用户注册时的手机号

### `create_time`

- 创建时间
- 类型：datetime
- 默认值：当前时间
- 不可空
作用：用户注册时的时间

### `delete_time`

- 注销时间
- 类型：datetime
- 默认值：NULL
- 可空
作用：用户注销时的时间
## 权限表 - roles
2.roles

| 字段名      | 数据类型    | 说明   | 备注          |
| ----------- | ----------- | ------ | ------------- |
| id          | bigint      | 主键   | 自增          |
| name        | varchar(32) | 角色名 | 唯一          |
| permissions | bigint      | 权限   | 例如：1 管理员权限，2 用户权限 |

## 用户角色表 - user_roles
3.user_roles

| 字段名  | 数据类型 | 说明     | 备注     |
| ------- | -------- | -------- | -------- |
| user_id | bigint   | 用户ID   | 逻辑外键 |
| role_id | bigint   | 角色ID   | 逻辑外键 |

## 镜像表 - image
4.image

| 字段名         | 数据类型         | 说明     | 备注   |
|:------------|--------------|--------|------|
| id          | bigint       | 主键     | 自增   |
| image_id    | varchar(60)  | 镜像id   | 唯一   |
| name        | varchar(50)  | 镜像名    |      |
| image       | varchar(255) | 镜像图片   |      |
| tag         | varchar(20)  | 镜像标签   |      |
| size        | varcahr(10)  | 镜像大小   |      |
| create_time | datetime     | 镜像创建时间 |      |
| delete_time | datetime     | 镜像删除时间 |      |
| is_active   | boolean      | 镜像是否启用 |      |
| owner_id    | bigint       | 所有者ID  | 逻辑外键 |


## 容器表 - container
5.container

| 字段名              | 数据类型         | 说明       | 备注              |
|:-----------------|--------------|----------|-----------------|
| id               | bigint       | id       | 自增              |
| container_id     | varchar(60)  | 容器id     | 唯一              |
| host_port        | bigint       | 主机端口映射   | 用于VNC服务访问，非空    |
| image_id         | varchar(12)  | 镜像id     | 逻辑外键            |
| name             | varcahr(30)  | 容器名      |                 |
| image            | varchar(255) | 容器图片     |                 |
| create_time      | datetime     | 容器创建时间   |                 |
| owner_id         | bigint       | 所有人id    | 逻辑外键            |
| last_access_time | datetime     | 容器最后访问时间 |                 |
| delete_time      | datetime     | 容器删除时间   |                 |
| is_active        | boolean      | 容器是否启用   |                 |
| status           | int          | 容器状态     | 1 启动 0 关闭 -1 销毁 |