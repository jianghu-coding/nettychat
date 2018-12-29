package com.chat.androidclient.mvvm.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lps on 2018/12/29 15:35.
 * 好友实体
 */
@Entity
public class Friend {
    @Id
    private Long id;
    private Long userId;
    private Long customid;
    private String headprofile;
    private String nickname;
    private String devicesAndState;
    private String sign;
    @Generated(hash = 380271733)
    public Friend(Long id, Long userId, Long customid, String headprofile,
            String nickname, String devicesAndState, String sign) {
        this.id = id;
        this.userId = userId;
        this.customid = customid;
        this.headprofile = headprofile;
        this.nickname = nickname;
        this.devicesAndState = devicesAndState;
        this.sign = sign;
    }
    @Generated(hash = 287143722)
    public Friend() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getHeadprofile() {
        return this.headprofile;
    }
    public void setHeadprofile(String headprofile) {
        this.headprofile = headprofile;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getDevicesAndState() {
        return this.devicesAndState;
    }
    public void setDevicesAndState(String devicesAndState) {
        this.devicesAndState = devicesAndState;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public Long getCustomid() {
        return this.customid;
    }
    public void setCustomid(Long customid) {
        this.customid = customid;
    }

}
