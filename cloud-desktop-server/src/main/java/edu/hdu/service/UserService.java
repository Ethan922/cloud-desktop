package edu.hdu.service;


import edu.hdu.dto.ChangePasswordDTO;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.dto.UserPageQueryDTO;
import edu.hdu.dto.UserSignupDTO;
import edu.hdu.result.PageResult;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserSignupVO;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    UserSignupVO signup(UserSignupDTO userSignupDTO);

    void logoff(Long id);

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void changeActiveness(Long id);

    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);
}
