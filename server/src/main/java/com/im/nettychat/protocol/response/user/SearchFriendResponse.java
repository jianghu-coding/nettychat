package com.im.nettychat.protocol.response.user;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.dto.UserDTO;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/22.
 */
@Data
public class SearchFriendResponse extends UserResponse {

    private List<UserDTO> users;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_FRIEND_RESPONSE;
    }
}
