package org.Lightspot.live.api.Controller;

import org.Lightspot.live.user.dto.UserDTO;
import org.Lightspot.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private IUserRpc userRpc;
    @RequestMapping("/getUserInfo")
    public UserDTO getUserInfo(Long userId){
        return userRpc.getByUserId(userId);
    }

    @RequestMapping("/updateUserInfo")
    public boolean updateUserInfo(Long userId, String nickName){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName(nickName);
        return userRpc.updateUserInfo(userDTO);
    }
    @RequestMapping("/insertUserInfo")
    public boolean insertUserInfo(Long userId){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName("test");
        userDTO.setSex(1);
        return userRpc.insertOne(userDTO);
    }
}
