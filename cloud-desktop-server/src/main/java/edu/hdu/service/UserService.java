package edu.hdu.service;


import edu.hdu.dto.UserLoginDTO;
import edu.hdu.entity.User;
import edu.hdu.vo.UserLoginVO;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
