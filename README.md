### Spring boot 业务小组件
+ 基于spring boot starter封装，适合简单的springboot项目
+ 数据库默认使用mysql，脚本存放于src/main/resources/sql/下
+ 快速集成项目中常用的基础功能，组件已包含controller、service、mybatis plus mapper | mapper xml

### 依赖包
+ org.projectlombok:lombok:1.18.20
+ cn.hutool:hutool-all:5.7.20
+ org.apache.rocketmq:rocketmq-spring-boot-starter:2.2.2
+ org.springframework.boot:spring-boot-starter-data-redis:2.4.5
+ org.springframework.boot:spring-boot-starter:2.4.5
+ com.xuxueli:xxl-job-core:2.3.0
+ com.baomidou:mybatis-plus-boot-starter:3.5.7
+ org.springdoc:springdoc-openapi-ui:1.6.9

#### xm-base（基础组件）
基础组件，定义全局的异常类、响应码、基础model对象、基础查询对象、用户信息接口等
#### xm-web-config(spring boot web项目配置)
spring boot web项目配置，包含全局异常处理、全局响应码、全局参数校验、全局参数绑定、全局参数转换等
#### xm-area（区域信息）
区域信息，包含省市区、街道等
#### xm-auth（权限管理）
spring boot拦截器实现的基于url请求地址的简单认证
#### xm-dict（字典）
字典，包含字典类型、字典数据
#### xm-file（文件上传管理）
文件上传管理，包含文件上传、文件下载、文件删除等
支持本地文件存储和阿里云oss存储
#### xm-file-template（模板文件管理）
模板文件管理，包含模板文件上传、模板文件下载、模板文件删除等
#### xm-log-view（服务器日志查询）
服务器日志查询
#### xm-logback-output（logback 日志输出扩展，已集成钉钉和xxl-job）
logback 日志输出扩展，已集成钉钉和xxl-job
#### xm-mybatis （mybatis-plus 配置）
mybatis-plus 配置
#### xm-redis（redis 配置）
redis 配置
#### xm-request-log（web请求日志）
web请求日志、存储、查看
#### xm-rocketmq（rocketmq 封装）
rocketmq 封装,可动态配置消费端
#### xm-serial（业务序列号，基于数据库锁）
业务序列号，基于数据库锁，用于生成项目中的单据号
#### xm-sms（短信发送，集成阿里云短信服务）
短信发送，集成阿里云短信服务
#### xm-socket（netty封装）
netty封装
#### xm-task（异步任务，依赖xm-rocketmq）
异步任务，依赖xm-rocketmq
#### xm-umrp(用户 菜单 角色 权限封装)
用户 菜单 角色 权限封装
#### xm-variable（系统变量管理）
系统变量管理
#### xm-xxljob（xxl-job 封装）
xxl-job 封装
#### xm-test（测试应用）
 测试应用