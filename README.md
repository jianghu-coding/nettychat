## nettychat
    服务端：基于netty4.16, redis, java8编写
    客户端: 安卓

## 开始项目重构
存储层用 mysql + redis, 纯 redis 实现， 分支 1.0

## 项目启动前配置
    1. jdk >= 1.8
    2. 安装配置redis, 无需设置账号密码
    3. 配置 server.properties
    4. mvn clean install -Dmaven.test.skip=true
       mvn assembly:assembly -Dmaven.test.skip=true // 将依赖一起打包
       // 部署环境需要指定 -Xms -Xmx 等配置信息, 默认10M或者较小会产生多次full gc以及eden会内存溢出
       // nohup java -Xms300M -Xmx300M  -jar server-1.0-jar-with-dependencies.jar > /data/log/chat.log &
    5. nohup java -jar server-1.0-jar-with-dependencies.jar > /data/log/chat.log &

## idea配置
    idea plugins lombok 配置才能配合使用  @Data注解
    
## 已经完成的功能如下
    1. 登录, 注册, 添加好友, 单聊, 好友列表
    2. 创建群组, 添加群组, 拉人进入群组, 群聊
    3. 离线消息推送，超时配置检测及开启
    4. 耗时业务放入线程池处理避免阻塞以至于降低并发吞吐量
    5. cglib动态代理service任务异步处理
    6. cglib动态代理业务线程池FastThreadLocal存储JEDIS，避免每次操作都需要反复的获取JEDIS资源
    
## 待完善的功能
    存储采用的redis, 对于数据一致性很高的场景不适用，大家可以参考以下策略进行处理
    
## 一致性场景举列
    创建群组的时候，会维护群组的信息，比如他有哪些群组列表，二者缺一不可
    ..等等
    
## 解决方案探讨
    1. 采用关系型数据库, 缺点: 高并发情况下，会极大的降低并发和吞吐量，大量池中连接获取等待超时，mysql服务崩溃，当然可以进行限流但是本质上来说大  大的降低了并发量
    2. 采用日志跟踪处理，服务一直处于高可用状态，极少情况出现操作失败，如果失败根据日志选择策略进行处理，是删除，恢复等等
    3. 操作一个比如创建群组后，其它的任务丢给消息队列(redis, ..mq), 依次消费处理日志记录保证正确性，如果采用redis list可以用定时任务依次pop处理, 如果有操作失败的日志记录，再根据策略来处理操作失败的
    4. 等等
    
## 接口文档见 doc.md

## 客户端测试
    安卓还没有写好, 用端口访问测试
        见MainTest.java
    初始化配置账号2个
    账号, 密码
    666666,777777
    888888,999999

## 服务端配置
    ip: 132.232.151.6
    port: 8888
    
## 请求指令
    定义: common/Command.java

## 协议规范
    首部4个字节 "魔数" 标识 协议认证
    int MAGIC_NUMBER = 0x12345678; // 魔数
    byte VERSION = 1; // 版本
    byte SERIALIZER = 1; // 现目前默认 1: com.alibaba.fastjson.JSON
    byte command = command; // 上述指令中选择
    byte length = length; // 数据长度

## 使用讲解
    com.im.nettychat.ClientTest.java // 有详细测试用列操作展示如下
    为了写测试用列方便request, response都复用了server的, 客户端可以自己定义取消不要的字段
    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    q
    请输入名称, 用户名, 密码用,隔开
    系统用户-1,666666,777777
    成功: [ RegisterResponse(userId=1, name=系统用户-1, icon=null, desc=null) ]

    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    b
    请输入对方的id和发送的消息用,隔开: 
    2,nihaoma
    ---接收对方消息此处
    成功: [ MessageResponse(fromUserId=2, message=wohenhao) ]

    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    e
    请输入要加入的群组的id: 
    1
    失败: [ 没有找到对应的群组]
    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    c
    请输入群组名称
    系统群组-1
    请输入拉取的用户id用,隔开
    2
    成功: [ CreateGroupResponse(groupName=null, groupId=1, userIds=null, icon=null) ]
    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    d
    请输入群组id
    1
    成功: [ GetUserGroupResponse(groupName=系统群组-1, groupId=1, userIds=[2, 1], icon=null, owner=1, desc=null) ]

    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    f
    请输入群组的id和发送消息用,隔开
    1,dajiahao
    成功: [ SendGroupMessageResponse(sendUserId=2, message=nihaoa) ]

    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    g
    请输入对方的id: 
    2
    成功: [ AddFriendResponse() ]

    ---输入指令 -> a: 登录, b: 发送消息, c: 创建群组, d: 获取群组信息, e: 加入群组, f: 发送群组消息
    ---输入指令 -> q: 注册, g: 添加好友, h: 获取好友信息列表
    h
    成功: [ GetFriendResponse(friends=[User(id=2, username=888888, password=null, name=系统用户-2, icon=null, desc=null)]) ]
    
 
## Android 第一版基础版即将完成，效果图
![image1](https://github.com/hejianglong/nettychat/blob/master/AndroidClient/pic/Screenshot_20190109-170721.jpg)
![image1](https://github.com/hejianglong/nettychat/blob/master/AndroidClient/pic/Screenshot_20190103-131011.jpg)
![image2](https://github.com/hejianglong/nettychat/blob/master/AndroidClient/pic/Screenshot_20190103-131013.jpg)
![image3](https://github.com/hejianglong/nettychat/blob/master/AndroidClient/pic/Screenshot_20190103-131020.jpg)


 ## 待更新内容:    
    表情, 图片传输
    自动扫描装载handler和对应指令避免每次手动操作
