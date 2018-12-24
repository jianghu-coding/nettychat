package com.im.nettychat.protocol.response.offline;

import com.im.nettychat.common.Command;
import com.im.nettychat.model.OfflineMessage;
import com.im.nettychat.protocol.PacketResponse;
import lombok.Data;
import java.util.List;

/**
 * 推送离线消息
 * @author hejianglong
 * @date 2018/12/24.
 */
@Data
public class OfflineMessageResponse extends PacketResponse {

    List<OfflineMessage> messages;

    @Override
    public Byte getCommand() {
        return Command.OFFLINE_MESSAGE_RESPONSE;
    }
}
