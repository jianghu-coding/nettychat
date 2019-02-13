package com.chat.androidclient.mvvm.model;

public enum TYPE {
        /**
         * PERSON 好友
         * GROUP 群组
         */
        PERSON(0), GROUP(1);

        private int id;

        TYPE(int mId) {
            id = mId;
        }

    public int getId() {
        return id;
    }

    public TYPE setId(int mId) {
        id = mId;
        return this;
    }
}