package org.Lightspot.live.api.Controller;

import org.Lightspot.live.user.dto.UserDTO;
import org.Lightspot.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private IUserRpc userRpc;
    @RequestMapping("/getUserInfo")
    public UserDTO getUserInfo(@RequestParam Long userId){
        return userRpc.getByUserId(userId);
    }

    @RequestMapping("/updateUserInfo")
    public boolean updateUserInfo(@RequestParam Long userId,
                                  @RequestParam(required = false) String nickName,
                                  @RequestParam(required = false) String nickname){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName(nickName != null ? nickName : nickname);
        return userRpc.updateUserInfo(userDTO);
    }
    @RequestMapping("/insertUserInfo")
    public boolean insertUserInfo(@RequestParam Long userId){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName("test");
        userDTO.setSex(1);
        return userRpc.insertOne(userDTO);
    }
}
