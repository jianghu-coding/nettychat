package com.im.nettychat.protocol.request.group;

import com.im.nettychat.common.Command;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2018/12/24.
 */
@Data
public class GetUserGroupListRequest extends UserGroupRequest{

    @Override
    public Byte getCommand() {
        return Command.GET_USER_GROUP_LIST;
    }
}
