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

### Tutorial comment rule — appear only once

Each annotation / concept is explained in full **only in its first file**. All subsequent uses write standard Javadoc without tutorial.

| Concept | First file (full tutorial) | Other files (concise only) |
|---------|---------------------------|---------------------------|
| @Data / @AllArgsConstructor / @NoArgsConstructor / @Builder | `Employee.java` | DTOs/VOs: class Javadoc only |
| @Schema | `EmployeeLoginDTO.java` | Other DTOs/VOs: no @Schema tutorial |
| DTO concept | `EmployeeLoginDTO.java` | Other DTOs: class Javadoc only |
| VO concept | `EmployeeLoginVO.java` | Other VOs: class Javadoc only |
| @Component | `JwtTokenAdminInterceptor.java` | `JwtProperties.java`: concise |
| @Configuration / @Bean / WebMvcConfigurer | `WebMvcConfiguration.java` | — |
| @RestController / @RequestMapping / HTTP mappings | `EmployeeController.java` | — |
| @Service | `EmployeeServiceImpl.java` | — |
| @Mapper | `EmployeeMapper.java` | — |
| @Slf4j | `WebMvcConfiguration.java` | Others: no @Slf4j tutorial |
| ThreadLocal | `BaseContext.java` | Usage sites: no re-explanation |
| Serializable / Result wrapper | `Result.java` | Others: no re-explanation |

### Javadoc format

```java
// 类 — 一行简述，首次出现的注解加教程说明
/** 员工服务实现类
 * @Service — 标记为服务层组件，Spring自动扫描注册（首次出现）
 */
@Service

// 后续出现的同类注解 — 只写简述
/** 员工Mapper接口 */
@Mapper

// 方法 — 简述 + @param + @return（不显然时省略@return）
/**
 * 分页查询员工
 * @param dto 查询条件
 * @return 分页结果
 */

// 字段 — // 注释 + @Schema
// 用户名
@Schema(description = "用户名")
private String username;
```

### Inline comments

- Multi-step logic: `// 1. ...` → `// 2. ...` → `// 3. ...`
- Single-line explanation: `// 简要说明`
- API/method call explanation: only on first occurrence in project

### Log format

统一使用 `log.info/error("描述: {}", arg)` — 冒号后加空格：

```java
log.info("员工登录: {}", username);      // ✓
log.info("员工登录:{}", username);        // ✗ 缺少空格
log.info("分页查询员工,查询参数:{}", dto); // ✗ 中文逗号
```

### In-file code style

- **Constructor injection**: `private final` field + constructor, no `@Autowired`
- **BeanUtils.copyProperties**: DTO → Entity or Entity → VO, never expose Entity directly in VO
- **PageHelper**: `startPage()` must be immediately before the Mapper call, no queries in between
- **ThreadLocal cleanup**: call `BaseContext.removeThreadLocal()` right after `getThreadLocal()`, in the same method
- **Exception throwing**: throw specific subclass of `BaseException` (e.g. `AccountNotFoundException`), never raw `BaseException`
- **Controller return**: always wrap in `Result.success()` or `Result.error()`, never return bare objects

### MyBatis XML conventions

- `namespace` must match the Mapper interface fully-qualified name
- `resultType` uses type alias (`Employee` not `com.sky.entity.Employee`)
- SQL keywords in uppercase: `SELECT`, `FROM`, `WHERE`, `INSERT INTO`, `UPDATE`, `ORDER BY`
- `<if test>` for String: `!= null and != ''`; for other types: `!= null` only
- `<where>` wraps optional filter conditions; `<set>` wraps dynamic update columns

### Naming conventions

- **Controller methods**: verb-based (`login`, `save`, `page`, `startOrStop`, `getById`, `update`)
- **Service interface**: same as controller method names
- **Mapper methods**: `getByUsername`, `save`, `page`, `update`, `getById`
- **DTOs**: `*DTO` suffix (e.g. `EmployeeLoginDTO`, `EmployeeQueryDTO`)
- **VOs**: `*VO` suffix (e.g. `EmployeeVO`, `EmployeeLoginVO`)
- **Constants**: `*Constant` suffix, fields in `UPPER_SNAKE_CASE`
- **Exceptions**: `*Exception` suffix, extending `BaseException`