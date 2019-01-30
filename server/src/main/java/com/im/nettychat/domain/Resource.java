package com.im.nettychat.domain;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class Resource {

    private Long id;

    private Integer type;

    private String value;

    private Long parentId;
}
