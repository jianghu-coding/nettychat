package com.im.nettychat.protocol.request;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.PacketRequest;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class SearchResourceRequest extends PacketRequest {

    private Integer type;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_RESOURCES;
    }
}
