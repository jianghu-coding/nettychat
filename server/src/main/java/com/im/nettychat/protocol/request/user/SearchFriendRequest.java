package com.im.nettychat.protocol.request.user;

import com.im.nettychat.common.Command;
import com.im.nettychat.common.Pagination;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/22.
 */
@Data
public class SearchFriendRequest extends UserRequest {

    private String name;

    private String username;

    private Pagination pagination;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_FRIEND;
    }
}
