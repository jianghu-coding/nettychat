package com.chat.androidclient.mvvm.model;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public interface Command {

    // request
    Byte LOGIN = 1;

    Byte SEND_MESSAGE = 2;

    Byte REGISTER = 4;

    Byte CREATE_GROUP = 6;

    Byte JOIN_GROUP = 8;

    Byte GET_USER_GROUP = 10;

    Byte SEND_GROUP_MESSAGE = 12;

    Byte ADD_FRIEND = 14;

    Byte GET_FRIENDS = 16;

    Byte GET_USER_GROUP_LIST = 18;

    // response
    Byte LOGIN_RESPONSE = 3;

    Byte REGISTER_RESPONSE = 5;

    Byte SEND_MESSAGE_RESPONSE = 7;

    Byte CREATE_GROUP_RESPONSE = 9;

    Byte JOIN_GROUP_RESPONSE = 11;

    Byte GET_USER_GROUP_RESPONSE = 13;

    Byte SEND_GROUP_MESSAGE_RESPONSE = 15;

    Byte ADD_FRIEND_RESPONSE = 17;

    Byte GET_FRIENDS_RESPONSE = 19;

    Byte GET_USER_GROUP_LIST_RESPONSE = 21;
}
