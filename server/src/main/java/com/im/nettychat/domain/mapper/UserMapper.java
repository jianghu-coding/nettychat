package com.im.nettychat.domain.mapper;

import com.im.nettychat.domain.User;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
public interface UserMapper {

    User findById(Long id);

    User findByUsername(String username);

    void save(User user);

    List<User> findByLikeName(String name);
}
