package com.im.nettychat.protocol.dto;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
@Data
public class UserDTO {

    private Long id;

    private String name;

    private String username;

    private String icon;

    private String desc;
}
