package org.Lightspot.live.user.provider.service;

import org.Lightspot.live.user.dto.UserDTO;

public interface IUserService {
    UserDTO getByUserId(Long userId);
    boolean updateUserInfo(UserDTO userDTO);
    boolean insertOne(UserDTO userDTO);
}
