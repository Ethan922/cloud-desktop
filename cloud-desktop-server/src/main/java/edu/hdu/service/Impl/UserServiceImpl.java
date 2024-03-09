package edu.hdu.service.Impl;

import edu.hdu.constant.JwtClaimsConstant;
import edu.hdu.constant.MessageConstant;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.entity.User;
import edu.hdu.entity.UserRoles;
import edu.hdu.exception.AccountNotFoundException;
import edu.hdu.exception.PasswordErrorException;
import edu.hdu.mapper.UserMapper;
import edu.hdu.mapper.UserRolesMapper;
import edu.hdu.properties.JwtProperties;
import edu.hdu.service.UserService;
import edu.hdu.utils.JwtUtil;
import edu.hdu.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRolesMapper userRolesMapper;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String username=userLoginDTO.getUsername();
        String password=userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        if (user==null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password= DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //获取用户权限
        UserRoles userRoles = userRolesMapper.getByUserId(user.getId());

        //登录成功后，下发jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        return UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .permissions(userRoles.getPermissions())
                .build();
    }
}
