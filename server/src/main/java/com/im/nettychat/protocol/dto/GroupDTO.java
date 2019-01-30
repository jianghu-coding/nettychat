package com.im.nettychat.protocol.dto;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@Data
public class GroupDTO {

        private Long id;

        /**
         * 群的创建人
         */
        private Long ownerId;

        private String name;

        private String icon;

        private String desc;

        /**
         * 群人数
         */
        private Integer num;
}