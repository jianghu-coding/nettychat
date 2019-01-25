package com.im.nettychat.domain;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/25.
 */
@Data
public class Group {

    private Long id;

    /**
     * 群的创建人
     */
    private Long ownerId;

    private String name;

    private String icon;

    private String desc;
}
