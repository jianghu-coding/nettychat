package com.im.nettychat.protocol.request;

import com.im.nettychat.protocol.PacketRequest;
import lombok.Data;
import static com.im.nettychat.common.Command.LOGIN;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@Data
public class LoginRequest extends PacketRequest {

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN;
    }
}
