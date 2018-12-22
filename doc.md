# 注意

# 错误处理
每个response都包含2个字段

    error: true, // 如果error为true, errorInfo展示错误信息
    errorInfo: "xxx" 
    
# 请求注意添加协议内容
    int MAGIC_NUMBER = 0x12345678; // 魔数 4个字节
    byte VERSION = 1; // 版本
    byte SERIALIZER = 1; // 现目前默认 1: com.alibaba.fastjson.JSON
    byte command = command; // 上述指令中选择
    byte length = length; // 数据长度
    
# 接口 
### 注册
request:
    
    (byte) version: 1, 
    (string) name: "名称", 
    (string) username: "用户名", 
    (long) userId: "用户id", 
    (string) desc: "用户简洁", 
    (byte) command: 4
    
response:

    (byte) version: 1, 
    (string) icon: "头像", 
    (string) username: "用户名", 
    (string) password: "密码", 
    (byte) command: 5    
### 登录
request:
    
    (byte) version: 1, 
    (string) username: "用户名", 
    (string) password: "密码", 
    (byte) command: 1
    
response:

    (byte) version: 1, 
    (long) userId: 12345,
    (string) icon: "头像", 
    (string) name: "用户名", 
    (string) desc: "个人描述",
    (byte) command: 3    
### 客户端互聊
request:
    
    (byte) version: 1, 
    (string) toUserId: 1234", // 发给谁
    (string) message: "内容", 
    (byte) command: 2
    
response:

    (byte) version: 1, 
    (long) fromUserId: 123, // 谁发的 
    (string) message: "内容", 
    (byte) command: 7   