package com.im.nettychat.common;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public interface Command {

    Byte LOGIN = 1;

    Byte SEND_MESSAGE = 2;

    Byte LOGIN_RESPONSE = 3;

    Byte REGISTER = 4;
}
