package edu.hdu.service.Impl;

import edu.hdu.constant.JwtClaimsConstant;
import edu.hdu.constant.MessageConstant;
import edu.hdu.constant.RolesConstant;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.dto.UserSignupDTO;
import edu.hdu.entity.Roles;
import edu.hdu.entity.User;
import edu.hdu.entity.UserRoles;
import edu.hdu.exception.AccountNotActiveException;
import edu.hdu.exception.AccountNotFoundException;
import edu.hdu.exception.PasswordErrorException;
import edu.hdu.exception.UsernameAlreadySignupException;
import edu.hdu.mapper.RolesMapper;
import edu.hdu.mapper.UserMapper;
import edu.hdu.mapper.UserRolesMapper;
import edu.hdu.properties.JwtProperties;
import edu.hdu.service.UserService;
import edu.hdu.utils.JwtUtil;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserSignupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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

    @Autowired
    private RolesMapper rolesMapper;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        //账号不存在
        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //账号未启用
        if (!user.getIsActive()) {
            throw new AccountNotActiveException(MessageConstant.ACCOUNT_NOT_ACTIVE);
        }
        //密码错误
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
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

    /**
     * 用户注册
     *
     * @param userSignupDTO
     * @return
     */
    @Override
    @Transactional
    public UserSignupVO signup(UserSignupDTO userSignupDTO) {
        User user = userMapper.getByUsername(userSignupDTO.getUsername());

        //用户名已注册
        if (user != null) {
            throw new UsernameAlreadySignupException(MessageConstant.USERNAME_ALREADY_SIGNUP);
        }

        user = new User();
        BeanUtils.copyProperties(userSignupDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //插入用户
        userMapper.insert(user);

        Roles roles=rolesMapper.getByName(RolesConstant.USER);
        //插入用户权限
        UserRoles userRoles= UserRoles.builder()
                .roleId(roles.getId())
                .userId(user.getId())
                .name(roles.getName())
                .permissions(roles.getPermissions())
                .build();

        userRolesMapper.insert(userRoles);

        return UserSignupVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
