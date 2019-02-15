package com.chat.androidclient.mvvm.model;

public enum ConverSationTYPE {
        /**
         * PERSON 好友
         * GROUP 群组
         */
        PERSON(0), GROUP(1);

        private int id;

        ConverSationTYPE(int mId) {
            id = mId;
        }

    public int getId() {
        return id;
    }

    public ConverSationTYPE setId(int mId) {
        id = mId;
        return this;
    }
}