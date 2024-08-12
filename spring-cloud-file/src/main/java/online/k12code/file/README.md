# 访问本地资源 - 6-28
- 继承WebMvcConfiguration
  - 重写addResourceHandlers方法，通过配置文件的方式添加新的本地资源映射路径
# 个资源文件添加权限 - 6-29
  - 首先引入oauth2-resource 依赖
    ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
        </dependency>
    ```
  - 添加一个配置文件，在其中注入public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity)
   这个方法，认证资源jwt添加自定义的公钥，用于passport中认证