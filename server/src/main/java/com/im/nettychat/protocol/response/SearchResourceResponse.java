package com.im.nettychat.protocol.response;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.PacketResponse;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class SearchResourceResponse extends PacketResponse {

    private List<String> values;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_RESOURCES_RESPONSE;
    }
}
