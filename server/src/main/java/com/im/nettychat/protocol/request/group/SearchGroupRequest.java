package com.im.nettychat.protocol.request.group;

import com.im.nettychat.common.Command;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class SearchGroupRequest extends UserGroupRequest {

    private String name;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_GROUP;
    }
}
