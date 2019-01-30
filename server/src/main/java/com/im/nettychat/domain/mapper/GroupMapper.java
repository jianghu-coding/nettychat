package com.im.nettychat.domain.mapper;

import com.im.nettychat.domain.Group;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/29.
 */
public interface GroupMapper {

    void save(Group group);

    List<Group> findByLikeName(String groupName);
}
