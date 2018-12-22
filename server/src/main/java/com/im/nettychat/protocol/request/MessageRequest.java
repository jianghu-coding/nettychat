package com.im.nettychat.protocol.request;

import com.im.nettychat.protocol.RequestPacket;
import lombok.Data;
import static com.im.nettychat.common.Command.SEND_MESSAGE;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@Data
public class MessageRequest extends RequestPacket {

    private Long toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return SEND_MESSAGE;
    }
}
