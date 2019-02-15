package com.chat.androidclient.mvvm.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author lps
 * @date 2018/12/29 15:35
 * 联系人实体
 */
@Entity
public class Contact {
    @Id
    private Long id;
    private Long userId;
    private Long customid;
    private String headprofile;
    private String nickname;
    private String devicesAndState;
    private String sign;
    @Convert(converter = ConversationTYPEConverter.class,columnType = Integer.class)
    private ConverSationTYPE type= ConverSationTYPE.PERSON;
    @Generated(hash = 821222579)
    public Contact(Long id, Long userId, Long customid, String headprofile,
            String nickname, String devicesAndState, String sign, ConverSationTYPE type) {
        this.id = id;
        this.userId = userId;
        this.customid = customid;
        this.headprofile = headprofile;
        this.nickname = nickname;
        this.devicesAndState = devicesAndState;
        this.sign = sign;
        this.type = type;
    }
    @Generated(hash = 672515148)
    public Contact() {
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
    public Long getCustomid() {
        return this.customid;
    }
    public void setCustomid(Long customid) {
        this.customid = customid;
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
    public ConverSationTYPE getType() {
        return this.type;
    }
    public void setType(ConverSationTYPE type) {
        this.type = type;
    }


public static class ConversationTYPEConverter implements PropertyConverter<ConverSationTYPE,Integer>{

    @Override
    public ConverSationTYPE convertToEntityProperty(Integer databaseValue) {
      if (databaseValue==null) {
          return null;
      }
      for (ConverSationTYPE type: ConverSationTYPE.values()){
          if (type.getId()==databaseValue){
              return type;
          }
      }
      return null;
    }

    @Override
    public Integer convertToDatabaseValue(ConverSationTYPE entityProperty) {
        return entityProperty==null?null:entityProperty.getId();
    }
}

}
