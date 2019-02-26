package com.chat.androidclient.mvvm.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lps on 2018/12/29 13:47.
 * 记录本地会话列表
 */
@Entity
public class Conversation {
    @Id
    private Long id;
    private Long fromId;
    private String lastcontent;
    private Long time;
    private int msgcount;
    @Convert(converter = Contact.ConversationTYPEConverter.class,columnType = Integer.class)
    private ConverSationTYPE type;
    @Generated(hash = 2108084932)
    public Conversation(Long id, Long fromId, String lastcontent, Long time, int msgcount,
            ConverSationTYPE type) {
        this.id = id;
        this.fromId = fromId;
        this.lastcontent = lastcontent;
        this.time = time;
        this.msgcount = msgcount;
        this.type = type;
    }
    @Generated(hash = 1893991898)
    public Conversation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getFromId() {
        return this.fromId;
    }
    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
    public String getLastcontent() {
        return this.lastcontent;
    }
    public void setLastcontent(String lastcontent) {
        this.lastcontent = lastcontent;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public int getMsgcount() {
        return this.msgcount;
    }
    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }
    public ConverSationTYPE getType() {
        return this.type;
    }
    public void setType(ConverSationTYPE type) {
        this.type = type;
    }



}
