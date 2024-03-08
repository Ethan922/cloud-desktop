package edu.hdu.service;


import edu.hdu.dto.UserLoginDTO;
import edu.hdu.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);
}
