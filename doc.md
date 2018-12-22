# 错误处理
每个response都包含2个字段

    error: true, // 如果error为true, errorInfo展示错误信息
    errorInfo: "xxx" 
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
    (string) username: "用户名", 
    (string) password: "密码", 
    (byte) command: 2
    
response:

    (byte) version: 1, 
    (long) toUserId: 123, // 发给谁 
    (string) message: "内容", 
    (byte) command: 7   