package com.im.nettychat.common;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午9:24
 */
public interface ErrorCode {

    String USER_NOT_FOUND = "userNotFound";

    String NEED_USERNAME_PASSWORD  = "needUsernamePassword";

    String USER_ALREADY_EXIST = "userAlreadyExist";

    String PASSWORD_ERROR = "passwordError";

    String NOT_SUPPORT_SEND_OFFLINE = "notSupportSendOffline";

    String GROUP_NAME_REQUIRED = "groupNameRequired";

    String GROUP_NAME_REPEAT = "groupNameRepeat";

    String GROUP_NOT_FOUND = "groupNotFound";

    String GROUP_USER_UPPER_LIMIT = "groupUserUpperLimit";

    String GROUP_USER_ALREADY_EXIST = "groupUserAlreadyExist";

    String GROUP_NOT_THIS_USER = "groupNotThisUser";

    String FRIEND_EXITS = "friendExits";
}
