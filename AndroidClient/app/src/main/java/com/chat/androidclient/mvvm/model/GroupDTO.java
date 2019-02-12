package com.chat.androidclient.mvvm.model;


import java.io.Serializable;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */

public class GroupDTO implements Serializable{

        private Long id;

        /**
         * 群的创建人
         */
        private Long ownerId;

        private String name;

        private String icon;

        private String desc;

        /**
         * 群人数
         */
        private Integer num;

        public Long getId() {
                return id;
        }

        public GroupDTO setId(Long mId) {
                id = mId;
                return this;
        }

        public Long getOwnerId() {
                return ownerId;
        }

        public GroupDTO setOwnerId(Long mOwnerId) {
                ownerId = mOwnerId;
                return this;
        }

        public String getName() {
                return name;
        }

        public GroupDTO setName(String mName) {
                name = mName;
                return this;
        }

        public String getIcon() {
                return icon;
        }

        public GroupDTO setIcon(String mIcon) {
                icon = mIcon;
                return this;
        }

        public String getDesc() {
                return desc;
        }

        public GroupDTO setDesc(String mDesc) {
                desc = mDesc;
                return this;
        }

        public Integer getNum() {
                return num;
        }

        public GroupDTO setNum(Integer mNum) {
                num = mNum;
                return this;
        }
}