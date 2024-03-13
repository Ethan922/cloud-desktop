package edu.hdu.service;


import edu.hdu.dto.*;
import edu.hdu.result.PageResult;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserQueryVO;
import edu.hdu.vo.UserSignupVO;
import edu.hdu.vo.UserVO;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    UserSignupVO signup(UserSignupDTO userSignupDTO);

    void logoff(Long id);

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void changeActiveness(Long id);

    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    UserQueryVO getById(Long id);

    void modify(UserModifyDTO userModifyDTO);
}
