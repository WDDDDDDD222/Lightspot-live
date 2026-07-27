package org.Lightspot.live.user.provider.service.impl;

import jakarta.annotation.Resource;
import org.Light.live.common.interfaces.ConvertBeanUtils;
import org.Lightspot.live.user.dto.UserDTO;
import org.Lightspot.live.user.provider.dao.mapper.IUserMapper;
import org.Lightspot.live.user.provider.dao.po.UserPO;
import org.Lightspot.live.user.provider.service.IUserService;
import org.apache.calcite.runtime.Resources;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserMapper userMapper;

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return 用户信息
     */
    @Override
    public UserDTO getByUserId(Long userId) {
        if (userMapper == null){
            return null;
        }
        return ConvertBeanUtils.convert(userMapper.selectById(userId),UserDTO.class);
    }

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    @Override
    public boolean updateUserInfo(UserDTO userDTO){
       if(userDTO == null || userDTO.getUserId() == null ){
           return false;
       }
       userMapper.updateById(ConvertBeanUtils.convert(userDTO,UserPO.class));
        return  true;
    }

    /**
     * 插入一条数据
     * @param userDTO
     * @return
     */
    @Override
    public boolean insertOne(UserDTO userDTO) {
        if(userDTO == null || userDTO.getUserId() == null){
            return false;
        }
        if (userMapper.selectById(userDTO.getUserId()) != null) {
            return false;
        }
        try {
            userMapper.insert(ConvertBeanUtils.convert(userDTO,UserPO.class)) ;
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }
}
