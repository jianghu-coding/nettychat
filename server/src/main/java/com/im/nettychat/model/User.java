package com.im.nettychat.model;

import com.im.nettychat.util.Util;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String icon;

    private String desc;

    public boolean isValidPassword(String password) {
        return Util.isValidPassword(password, this.password);
    }
}
