package com.im.nettychat.model;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2018/12/24.
 */
@Data
public class OfflineMessage {

    /**
     * 消息类型
     */
    private Byte messageType;

    /**
     * 内容
     */
    private String message;

    /**
     * 取决于messageType类型
     */
    private Long userId;

    private Long groupId;

    private int contentType;
}
