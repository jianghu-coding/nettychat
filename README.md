# nettychat
    基于netty4 IM

# 项目启动前配置
    1. jdk >= 1.8
    2. 安装配置redis, 无需设置账号密码
    3. 配置 server.properties
    4. mvn clean install -Dmaven.test.skip=true
       mvn assembly:assembly -Dmaven.test.skip=true // 将依赖一起打包
       // 部署环境需要指定 -Xms -Xmx 等配置信息, 默认10M或者较小会产生多次full gc以及eden会内存溢出
       // nohup java -Xms300M -Xmx300M  -jar server-1.0-jar-with-dependencies.jar > /data/log/chat.log &
    5. nohup java -jar server-1.0-jar-with-dependencies.jar > /data/log/chat.log &

# 客户端测试用列
    安卓还没有写好, 用端口访问测试
        见MainTest.java
    初始化配置账号2个
    账号, 密码
    666666,777777
    888888,999999

# 服务端配置
    ip: 114.115.248.101
    port: 8080
    
# 请求指令
    定义: common/Command.java
    
    // request
    Byte LOGIN = 1;

    Byte SEND_MESSAGE = 2;

    Byte REGISTER = 4;

    Byte CREATE_GROUP = 6;

    Byte JOIN_GROUP = 8;

    Byte GET_USER_GROUP = 10;

    Byte SEND_GROUP_MESSAGE = 12;

    Byte ADD_FRIEND = 14;

    Byte GET_FRIENDS = 16;

    // response
    Byte LOGIN_RESPONSE = 3;

    Byte REGISTER_RESPONSE = 5;

    Byte SEND_MESSAGE_RESPONSE = 7;

    Byte CREATE_GROUP_RESPONSE = 9;

    Byte JOIN_GROUP_RESPONSE = 11;

    Byte GET_USER_GROUP_RESPONSE = 13;

    Byte SEND_GROUP_MESSAGE_RESPONSE = 15;

    Byte ADD_FRIEND_RESPONSE = 17;

    Byte GET_FRIENDS_RESPONSE = 19;

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

![avatar](https://upload-images.jianshu.io/upload_images/11476787-b9c8f64d314e1e60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 接口文档见 doc.md