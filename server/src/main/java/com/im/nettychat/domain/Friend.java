package com.im.nettychat.domain;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
@Data
public class Friend {

    private Long id;

    private Long fromUserId;

    private Long toUserId;
}
