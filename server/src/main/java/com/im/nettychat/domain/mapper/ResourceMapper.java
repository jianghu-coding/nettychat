package com.im.nettychat.domain.mapper;

import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public interface ResourceMapper {

    List<String> findValuesByType(Integer type);
}
