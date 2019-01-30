package com.im.nettychat.protocol.response.group;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.dto.GroupDTO;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class SearchGroupResponse extends UserGroupResponse {

    private List<GroupDTO> groups;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_GROUP_RESPONSE;
    }
}
