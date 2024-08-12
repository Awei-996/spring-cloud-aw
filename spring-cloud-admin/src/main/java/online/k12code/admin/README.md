# admin监控的使用
-  分别在server端和客户端引入依赖
   - server      
   ```<dependency>
     <groupId>de.codecentric</groupId>
     <artifactId>spring-boot-admin-starter-server</artifactId>
     </dependency> 
   ```
   - client
   ```
       <dependency>
          <groupId>de.codecentric</groupId>
          <artifactId>spring-boot-admin-starter-client</artifactId>
        </dependency>
   ```
- 服务端启动类上添加@EnableAdminServer注解
- 客户端配置文件添加，用于在web上暴露那些端点，Actuator端点帮助你监控和管理应用程序，
比如健康检查、指标、环境信息等，在生产环境中不建议使用*来暴露所有的端点
  ```xml
  management:
    endpoints:
      web:
        exposure:
          include: '*'
  ```
- 如果存在oauth2添加白名单
  ```
  authorizeRequests.gexMatchers("^/actuator(/.*)?$").permitAll()
  ```