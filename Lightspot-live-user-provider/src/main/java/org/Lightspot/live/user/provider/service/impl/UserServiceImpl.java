package org.Lightspot.live.user.provider.service.impl;

import jakarta.annotation.Resource;
import org.Light.live.common.interfaces.ConvertBeanUtils;
import org.Lightspot.live.user.dto.UserDTO;
import org.Lightspot.live.user.provider.dao.mapper.IUserMapper;
import org.Lightspot.live.user.provider.dao.po.UserPO;
import org.Lightspot.live.user.provider.service.IUserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserMapper userMapper;
    @Resource
    private RedisTemplate<String, UserDTO> redisTemplate;

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
        String key = "userInfo:" + userId;
        UserDTO userDTO = getUserInfoFromCache(key);
        if (userDTO != null){
            return userDTO;
        }
        userDTO =ConvertBeanUtils.convert(userMapper.selectById(userId),UserDTO.class);
        if(userDTO != null){
            saveUserInfoToCache(key, userDTO);
        }
        return userDTO;
    }

    private UserDTO getUserInfoFromCache(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private void saveUserInfoToCache(String key, UserDTO userDTO) {
        try {
            redisTemplate.opsForValue().set(key,userDTO);
        } catch (RuntimeException ignored) {
        }
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
       if (hasNoUpdateField(userDTO)) {
           return false;
       }
       userMapper.updateById(ConvertBeanUtils.convert(userDTO,UserPO.class));
       deleteUserInfoCache(userDTO.getUserId());
        return  true;
    }

    private boolean hasNoUpdateField(UserDTO userDTO) {
        return userDTO.getNickName() == null
                && userDTO.getTrueName() == null
                && userDTO.getAvatar() == null
                && userDTO.getSex() == null
                && userDTO.getWorkCity() == null
                && userDTO.getBornCity() == null
                && userDTO.getBornDate() == null;
    }

    private void deleteUserInfoCache(Long userId) {
        try {
            redisTemplate.delete("userInfo:" + userId);
        } catch (RuntimeException ignored) {
        }
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
