# Spring Boot 后台管理脚手架

基于 Spring Boot 3.2 + MyBatis Plus + Spring Security + JWT 的后台管理系统脚手架。

## 技术栈

- **Spring Boot**: 3.2.0
- **MyBatis Plus**: 3.5.5
- **Spring Security**: 6.x
- **JWT**: jjwt 0.12.3
- **MySQL**: 8.0+
- **Lombok**: 简化代码
- **Hutool**: Java 工具库

## 项目结构

```
admin-scaffold/
├── src/main/java/com/example/admin/
│   ├── AdminApplication.java          # 启动类
│   ├── common/
│   │   ├── exception/
│   │   │   ├── BusinessException.java    # 业务异常
│   │   │   └── GlobalExceptionHandler.java # 全局异常处理
│   │   └── result/
│   │       └── Result.java               # 统一返回结果
│   ├── config/
│   │   ├── JwtAuthFilter.java         # JWT 认证过滤器
│   │   ├── MybatisPlusConfig.java     # MyBatis Plus 配置
│   │   ├── SecurityConfig.java        # Security 配置
│   │   └── WebSecurityConfig.java     # Web 安全配置
│   ├── controller/
│   │   ├── AuthController.java        # 认证控制器
│   │   └── UserController.java        # 用户管理控制器
│   ├── dto/
│   │   ├── LoginDTO.java              # 登录请求 DTO
│   │   └── LoginVO.java               # 登录响应 VO
│   ├── entity/
│   │   └── SysUser.java               # 用户实体
│   ├── mapper/
│   │   └── SysUserMapper.java         # 用户 Mapper
│   └── service/
│       ├── SysUserService.java        # 用户服务接口
│       └── impl/
│           └── SysUserServiceImpl.java # 用户服务实现
├── src/main/resources/
│   ├── application.yml                # 配置文件
│   └── mapper/
│       └── SysUserMapper.xml          # Mapper XML
├── sql/
│   └── schema.sql                     # 数据库脚本
└── pom.xml                            # Maven 配置
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 2. 数据库初始化

执行 `sql/schema.sql` 脚本创建数据库和表：

```bash
mysql -u root -p < sql/schema.sql
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/admin_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 运行项目

```bash
cd admin-scaffold
mvn spring-boot:run
```

### 5. 测试接口

#### 登录接口
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

#### 获取用户列表（需要 token）
```bash
curl -X GET http://localhost:8080/api/users?pageNum=1&pageSize=10 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 功能特性

- ✅ 统一返回结果封装
- ✅ 全局异常处理
- ✅ JWT Token 认证
- ✅ Spring Security 权限控制
- ✅ MyBatis Plus 分页插件
- ✅ 自动填充创建/更新时间
- ✅ 逻辑删除
- ✅ CORS 跨域配置
- ✅ 用户 CRUD 示例
- ✅ BCrypt 密码加密

## 默认账号

- 用户名：`admin`
- 密码：`admin123`

## 扩展开发

### 添加新模块

1. 在 `entity` 包创建实体类
2. 在 `mapper` 包创建 Mapper 接口
3. 在 `service` 包创建服务接口和实现
4. 在 `controller` 包创建控制器

### 权限控制

使用 `@PreAuthorize` 注解进行权限控制：

```java
@PreAuthorize("hasAnyAuthority('user:list')")
@GetMapping
public Result<Page<SysUser>> list() { ... }
```

## License

MIT
