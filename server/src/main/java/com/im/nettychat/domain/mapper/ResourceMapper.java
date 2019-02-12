package com.im.nettychat.domain.mapper;

import com.im.nettychat.domain.Resource;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public interface ResourceMapper {

    List<Resource> findValuesByType(Integer type);
}
