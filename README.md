# nettychat
基于netty的聊天工具, 某些场景需要并发事务控制, 现在没有做
后续会提取出来做处理，主要在以下几个点
1. 注册的时候

# server config
    ip: 114.115.248.101
    port: 8080
    
# 请求指令
    定义: common/Command.java
    
    // request
    Byte LOGIN = 1; // 登录请求
    Byte SEND_MESSAGE = 2; // 发送消息请求
    Byte REGISTER = 4; // 注册请求
    
    // response
    Byte LOGIN_RESPONSE = 3; // 登录成功响应
    Byte REGISTER_RESPONSE = 5; // 注册成功响应

# 协议规范
    首部4个字节 "魔数" 标识 协议认证
    int MAGIC_NUMBER = 0x12345678; // 魔数
    byte VERSION = 1; // 版本
    byte SERIALIZER = 1; // 现目前默认 1: com.alibaba.fastjson.JSON
    byte command = command; // 上述指令中选择
    byte length = length; // 数据长度

# 使用讲解
    请求对象体必须包含字段 command(请求指令), version(版本号)
    安卓 demo
    com.im.nettychat.ClientTest.java

# 接口
## 错误处理
每个返回对象都包含2个字段

    error: true, errorInfo: "xxx" // 如果error为true, errorInfo展示错误信息
    
### 注册
request 内容
    
    (byte) version: 1, 
    (string) name: "名称", 
    (string) username: "用户名", 
    (string) userId: "用户id", 
    (string) desc: "用户简洁", 
    (byte) command: 4
    
response 内容

    (byte) version: 1, 
    (string) icon: "头像", 
    (string) username: "用户名", 
    (string) password: "密码", 
    (byte) command: 5    
### 登录
### 发送消息