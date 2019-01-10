package com.im.nettychat.domain.mapper;

import com.im.nettychat.domain.User;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
public interface UserMapper {

    User findById(Long id);

    User findByUsername(String username);

    void save(User user);
}
