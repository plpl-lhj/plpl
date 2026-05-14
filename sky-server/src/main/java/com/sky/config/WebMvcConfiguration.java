package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，注册web相关组件
 * @Configuration - 标记该类为配置类，Spring会将其纳入Spring容器管理
 * @Slf4j - Lombok注解，自动生成log对象，用于日志记录
 * WebMvcConfigurer - 实现该接口用于自定义Spring MVC配置
 */
// @Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    public WebMvcConfiguration(JwtTokenAdminInterceptor jwtTokenAdminInterceptor) {
        this.jwtTokenAdminInterceptor = jwtTokenAdminInterceptor;
    }

    /**
     * 注册Jwt令牌校验拦截器
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册jwt拦截器...");
        // addInterceptor() - 添加拦截器实例
        // addPathPatterns() - 设置拦截的路径，/**表示拦截所有路径
        // excludePathPatterns() - 设置排除的路径，登录接口不需要拦截
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 通过springdoc生成接口文档
     * @Bean - 将方法的返回值对象注册到Spring容器中
     * OpenAPI - SpringDoc的OpenAPI配置对象，用于生成Swagger文档
     * @return OpenAPI配置对象
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .version("1.0")
                        .description("苍穹外卖项目接口文档"));
    }

//    /**
//     * 自定义ObjectMapper，替换Spring Boot默认的ObjectMapper
//     * Spring Boot会自动将容器中的ObjectMapper注入到消息转换器中
//     * Spring Boot 4.x推荐方式，低版本可使用下方注释的extendMessageConverters方式
//     */
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new JacksonObjectMapper();
//    }
//
//    /**
//     * 扩展消息转换器，将自定义的JacksonObjectMapper注册到Spring MVC
//     * extendMessageConverters - 在默认转换器列表基础上追加自定义转换器
//     * MappingJackson2HttpMessageConverter - Jackson的JSON消息转换器
//     * 将自定义转换器添加到列表最前面，优先使用
//     *
//     * 注意：Spring Boot 4.x中extendMessageConverters已标记为@Deprecated(forRemoval=true)
//     * 低版本Spring Boot（2.x/3.x）可使用此方式
//     */
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // 创建消息转换器，设置自定义的ObjectMapper
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(new JacksonObjectMapper());
//        // 添加到转换器列表最前面（优先级最高）
//        converters.addFirst(converter);
//    }
}
