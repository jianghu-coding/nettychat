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
### 创建群组
request:
    
    (byte) version: 1, 
    (string) groupName: "群组名称", 
    (List<Long>) userIds: [1,2,3], // 创建时拉取的人可以不拉人创建 
    (byte) command: 6
    
response:

    (byte) version: 1, 
    (string) groupName: "群组名称",
    (long) groupId: "群组ID", 
    (List<Long>) userIds: [1,2,3,4], // 群组人员
    (string) icon // 图标
    (byte) command: 9
### 加入群组
request:
    
    (byte) version: 1, 
    (long) groupId: 1234, // 加入群组的id
    (byte) command: 8
    
response:

    (byte) version: 1, 
    (long) groupId: 1234, // 加入群组的id
    (string) groupName: "群组名称",
    (string) icon // 图标
    (byte) command: 11
### 获取群组信息
request:
    
    (byte) version: 1, 
    (long) groupId: 1234, // 群组的id
    (byte) command: 10
    
response:

    (byte) version: 1, 
    (long) groupId: 1234, // 群组的id
    (long) owner: 12345, // 群主
    (string) groupName: "群组名称",
    (List<Long>) userIds: [1,2,3,4], // 群组人员
    (string) icon // 图标    (byte) command: 13
### 发送群组消息
request:
    
    (byte) version: 1, 
    (string) groupId: 12343, // 发给那个群组
    (string) message: "内容", 
    (byte) command: 2
    
response:

    (byte) version: 1, 
    (long) sendUserId: 123, // 谁发的信息
    (string) message: "内容", 
    (byte) command: 7
### 添加好友
request:
    
    (byte) version: 1, 
    (long) friendUserId: 12343, // 好友id
    (byte) command: 14
    
response:

    (byte) version: 1, 
    (byte) command: 17
### 我的好友列表
request:
    
    (byte) version: 1, 
    (byte) command: 16
    
response:

    (byte) version: 1, 
    (List<User>) friends, // 好友信息
    (byte) command: 17
    // 该接口返回的部分字段信息
    User: { 
        id, name, icon, desc
    }