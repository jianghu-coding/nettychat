package com.im.nettychat.protocol.response.group;

import com.im.nettychat.common.Command;
import com.im.nettychat.model.UserGroup;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @date 2018/12/24.
 */
@Data
public class GetUserGroupListResponse extends UserGroupResponse {

    private List<UserGroup> userGroups;

    @Override
    public Byte getCommand() {
        return Command.GET_USER_GROUP_LIST_RESPONSE;
    }
}
