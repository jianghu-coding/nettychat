package com.im.nettychat.domain;

import com.im.nettychat.util.Util;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
@Data
public class User {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String icon;

    private String desc;

    public boolean isValidPassword(String password) {
        return Util.isValidPassword(password, this.password);
    }
}
