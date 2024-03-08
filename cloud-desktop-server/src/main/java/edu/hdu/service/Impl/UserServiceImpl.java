package edu.hdu.service.Impl;

import edu.hdu.dto.UserLoginDTO;
import edu.hdu.entity.User;
import edu.hdu.mapper.UserMapper;
import edu.hdu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        return null;
    }
}
