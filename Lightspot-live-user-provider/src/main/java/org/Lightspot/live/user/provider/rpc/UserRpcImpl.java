package org.Lightspot.live.user.provider.rpc;

import jakarta.annotation.Resource;
import org.Light.live.common.interfaces.ConvertBeanUtils;
import org.Lightspot.live.user.dto.UserDTO;
import org.Lightspot.live.user.interfaces.IUserRpc;
import org.Lightspot.live.user.provider.service.IUserService;
import org.apache.calcite.runtime.Resources;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService
public class UserRpcImpl implements IUserRpc {
@Resource
    private IUserService userService;



@Override
    public org.Lightspot.live.user.dto.UserDTO getByUserId(Long userId) {
        return ConvertBeanUtils.convert(userService.getByUserId(userId), org.Lightspot.live.user.dto.UserDTO.class);
    }
    @Override
    public boolean updateUserInfo(UserDTO userDTO) {
        return userService.updateUserInfo(userDTO);
    }
    @Override
    public boolean insertOne(UserDTO userDTO) {
        return userService.insertOne(userDTO);
    }

}
