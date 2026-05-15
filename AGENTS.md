# AGENTS.md — 苍穹外卖 (sky)

## Project structure

Spring Boot 4.0.6 / Java 25 / Maven multi-module food delivery backend.

| Module | Purpose | Key packages |
|--------|---------|-------------|
| `sky-common` | Shared: utils, constants, exceptions, config props, `Result<T>`, `PageResult` | `.result`, `.utils`, `.constant`, `.exception`, `.properties`, `.context` |
| `sky-pojo` | Data carriers: entities, DTOs, VOs | `.entity`, `.dto`, `.vo` |
| `sky-server` | Runnable app: controllers, services, mappers, config | `.controller.*`, `.server`, `.mapper`, `.config`, `.handler`, `.interceptor` |

Entrypoint: `com.sky.SkyServerApplication` in `sky-server`.

## Commands

```powershell
mvn clean compile          # build all modules
mvn clean package -DskipTests  # package (there are no tests)
mvn spring-boot:run -pl sky-server  # run dev server
```

**No tests exist.** `.gitignore` excludes `*Test.java` and `**/test/`. Do not create test files.

## Key conventions

- **Constructor injection** everywhere — never use `@Autowired`.
- **Result wrapper**: `Result.success(data)` → `code=1`; `Result.error(msg)` → `code=0`. Not HTTP status-based.
- **Password hashing**: Argon2id via `PasswordUtil.hash()` / `PasswordUtil.verify()`. Default password: `"123456"` (`PasswordConstant.DEFAULT_PASSWORD`).
- **JWT auth**: Token in header named `token` (configured via `sky.jwt.admin-token-name`). Interceptor on `/admin/**` except `/admin/employee/login`.
- **ThreadLocal context**: `BaseContext.setThreadLocal(map)` / `getThreadLocal()` for cross-layer user ID. **Must call `removeThreadLocal()` after reading** (see `EmployeeServiceImpl.save()` pattern).
- **MyBatis mapper XML**: Located in `sky-server/src/main/resources/mapper/`. Type alias `com.sky.entity` enabled.
- **MyBatis dynamic SQL**: Uses `<set>` + `<if>` for updates; `<where>` + `<if>` for optional filters. String fields check `!= null and != ''`, other types check `!= null` only.
- **Pagination**: `PageHelper.startPage(page, pageSize)` immediately before the Mapper call.
- **Swagger UI**: `/swagger-ui/index.html` (SpringDoc OpenAPI).
- **Database**: MySQL `sky_take_out` on localhost:3306.

## Architecture notes

- Controllers in `com.sky.controller.admin/` (and likely `.user/` for future user-facing endpoints).
- Service interface in `com.sky.service`, impl in `.server.impl`.
- Mapper interfaces under `com.sky.mapper` are pure MyBatis (no JPA).
- Exception hierarchy: `BaseException` → `AccountNotFoundException`, `PasswordErrorException`, `AccountLockedException`.
- Global error handler `GlobalExceptionHandler` catches `DuplicateKeyException`, `BaseException`, and generic `Exception`.
- `JacksonObjectMapper` exists but is commented out — custom JSON mapper not currently wired.

## Code & comment conventions

### 注解教程规则 — 每种只出现一次

每个注解/概念在项目中**只在第一次出现的文件中详细解释**，后续使用只写简要说明。

| 注解/概念 | 首次出现文件（完整教程） | 其他文件（仅简述） |
|----------|------------------------|-------------------|
| @Data / @AllArgsConstructor / @NoArgsConstructor / @Builder | `Employee.java` | 其他Entity/DTO/VO：类Javadoc简述 |
| @Schema | `EmployeeLoginDTO.java` | 其他DTO/VO：无@Schema教程 |
| DTO概念 | `EmployeeLoginDTO.java` | 其他DTO：类Javadoc简述 |
| VO概念 | `EmployeeLoginVO.java` | 其他VO：类Javadoc简述 |
| @Component | `JwtTokenAdminInterceptor.java` | `JwtProperties.java`：简述 |
| @Configuration / @Bean / WebMvcConfigurer | `WebMvcConfiguration.java` | — |
| @RestController / @RequestMapping / HTTP mappings | `EmployeeController.java` | — |
| @Service | `EmployeeServiceImpl.java` | — |
| @Mapper | `EmployeeMapper.java` | — |
| @Slf4j | `WebMvcConfiguration.java` | 其他：无@Slf4j教程 |
| @Serial / Serializable | `Employee.java` | 其他：无教程 |
| ThreadLocal | `BaseContext.java` | 使用处：无重复解释 |
| Result封装 | `Result.java` | 其他：无重复解释 |
| @Transactional | `DishServiceImpl.java` | 其他：简述 |
| @AutoFill / @Aspect / @Before | `AutoFillAspect.java` | `AutoFill.java`：简述 |
| @RestControllerAdvice / @ExceptionHandler | `GlobalExceptionHandler.java` | — |

### Javadoc格式规范

```java
/**
 * 类简述
 * 详细说明（可选，首次出现时添加）
 *
 * @注解名 — 注解说明（仅首次出现时添加教程）
 */
@注解

/**
 * 方法简述
 * 详细说明（可选，复杂逻辑时添加）
 *
 * @param 参数名 参数说明
 * @return 返回值说明
 */

// 字段 — 单行注释说明用途
private String fieldName;
```

### 内联注释规范

- 多步逻辑：`// 1. ...` → `// 2. ...` → `// 3. ...`
- 单行说明：`// 简要说明`
- 空行分隔：每个逻辑块之间空一行

### 日志格式

统一使用 `log.info/error("描述: {}", arg)` — 冒号后加空格：

```java
log.info("员工登录: {}", username);      // ✓
log.info("员工登录:{}", username);        // ✗ 缺少空格
log.info("分页查询员工,查询参数:{}", dto); // ✗ 中文逗号
```

### 代码风格

- **构造器注入**: `private final` 字段 + 构造函数，不使用 `@Autowired`
- **属性拷贝**: BeanUtils.copyProperties(source, target)，DTO→Entity 或 Entity→VO
- **分页**: `PageHelper.startPage()` 必须紧挨Mapper调用
- **ThreadLocal清理**: 读取后立即调用 `removeThreadLocal()`
- **异常抛出**: 抛出具体子类（如 `AccountNotFoundException`），不直接抛 `BaseException`
- **Controller返回**: 统一包装为 `Result.success()` 或 `Result.error()`

### 变量命名规范

- **方法参数**: 使用语义化名称（如 `loginDTO`、`insertDTO`、`queryDTO`、`updateDTO`）
- **局部变量**: 使用有意义的名称（如 `employeeList`、`flavorList`、`currentUserId`）
- **常量**: 全大写下划线分隔（如 `SET_CREATE_TIME`、`DEFAULT_PASSWORD`）
- **布尔变量**: 使用 `is/has/can` 前缀

### MyBatis XML规范

- `namespace` 必须与Mapper接口全限定名一致
- `resultType` 使用类型别名（如 `Employee` 而非 `com.sky.entity.Employee`）
- SQL关键字大写：`SELECT`、`FROM`、`WHERE`、`INSERT INTO`、`UPDATE`、`ORDER BY`
- `<if test>` 字符串：`!= null and != ''`；其他类型：`!= null`
- `<where>` 包裹可选过滤条件；`<set>` 包裹动态更新列

### 命名约定

- **Controller方法**: 动词形式（`login`、`save`、`page`、`startOrStop`、`getById`、`update`）
- **Service接口**: 与Controller方法同名
- **Mapper方法**: `getByUsername`、`save`、`page`、`update`、`getById`
- **DTO类**: `*DTO` 后缀（如 `EmployeeLoginDTO`、`EmployeeQueryDTO`）
- **VO类**: `*VO` 后缀（如 `EmployeeVO`、`EmployeeLoginVO`）
- **常量类**: `*Constant` 后缀，字段 `UPPER_SNAKE_CASE`
- **异常类**: `*Exception` 后缀，继承 `BaseException`