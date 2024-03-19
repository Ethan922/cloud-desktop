package edu.hdu.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hdu.annotation.CheckPermission;
import edu.hdu.constant.JwtClaimsConstant;
import edu.hdu.constant.MessageConstant;
import edu.hdu.constant.PermissionConstant;
import edu.hdu.constant.RolesConstant;
import edu.hdu.context.BaseContext;
import edu.hdu.dto.*;
import edu.hdu.entity.Container;
import edu.hdu.entity.Role;
import edu.hdu.entity.User;
import edu.hdu.entity.UserRole;
import edu.hdu.exception.*;
import edu.hdu.mapper.ContainerMapper;
import edu.hdu.mapper.RoleMapper;
import edu.hdu.mapper.UserMapper;
import edu.hdu.mapper.UserRoleMapper;
import edu.hdu.properties.JwtProperties;
import edu.hdu.result.PageResult;
import edu.hdu.service.UserService;
import edu.hdu.utils.DockerUtils;
import edu.hdu.utils.JwtUtil;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserQueryVO;
import edu.hdu.vo.UserSignupVO;
import edu.hdu.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ContainerMapper containerMapper;

    @Autowired
    private DockerUtils dockerUtils;

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
        //账号被禁用
        if (!user.getIsActive()) {
            throw new AccountNotActiveException(MessageConstant.ACCOUNT_NOT_ACTIVE);
        }
        //密码错误
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //获取用户权限
        UserRole userRole = userRoleMapper.getByUserId(user.getId());
        Role role=roleMapper.getById(userRole.getRoleId());

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
                .permission(role.getPermission())
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
            throw new UsernameOccupiedException(MessageConstant.USERNAME_OCCUPIED);
        }

        user = new User();
        BeanUtils.copyProperties(userSignupDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //插入用户
        userMapper.insert(user);

        Role role = roleMapper.getByRoleName(RolesConstant.USER);
        //插入用户权限
        UserRole userRole = UserRole.builder()
                .roleId(role.getId())
                .userId(user.getId())
                .build();

        userRoleMapper.insert(userRole);

        return UserSignupVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    /**
     * 注销账户
     *
     * @param id
     */
    @Override
    @Transactional
    public void logoff(Long id) {

        User user = userMapper.getById(id);
        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //删除user表中的信息
        userMapper.delete(id);

        //删除user_roles表中的信息
        userRoleMapper.deleteByUserId(id);

        //清除该用户的所有容器
        List<Container> containers = containerMapper.getByUserId(id);
        if (containers != null && containers.size() == 0) {
            dockerUtils.removeContainers(containers);
        }

        //删除container表中的信息
        containerMapper.deleteByUserId(id);
    }

    /**
     * 修改密码
     *
     * @param changePasswordDTO
     */
    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        //旧密码与新密码相同
        if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
            throw new PasswordChangeFailedException(MessageConstant.TWO_PASSWORDS_SAME);
        }

        String oldPassword = DigestUtils.md5DigestAsHex(changePasswordDTO.getOldPassword().getBytes());
        User user = userMapper.getById(BaseContext.getCurrentUserId());

        //原密码错误
        if (!oldPassword.equals(user.getPassword())) {
            throw new PasswordChangeFailedException(MessageConstant.OLD_PASSWORD_ERROR);
        }
        String newPassword = DigestUtils.md5DigestAsHex(changePasswordDTO.getNewPassword().getBytes());
        userMapper.update(User.builder()
                .id(BaseContext.getCurrentUserId())
                .password(newPassword)
                .updateUser(BaseContext.getCurrentUserId())
                .updateTime(LocalDateTime.now())
                .build());
    }


    /**
     * 禁用或启用用户账号
     *
     * @param id
     */
    @Override
    @CheckPermission
    public void changeActiveness(Long id) {
        User user = userMapper.getById(id);
        userMapper.update(User.builder()
                .id(id)
                .isActive(!user.getIsActive())
                .updateUser(BaseContext.getCurrentUserId())
                .updateTime(LocalDateTime.now())
                .build());
    }

    /**
     * 用户分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    @Override
    @CheckPermission
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<UserVO> page = userMapper.pageQuery(userPageQueryDTO);
        return PageResult.builder()
                .total(page.getTotal())
                .records(page.getResult())
                .build();
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserQueryVO getById(Long id) {
        User user = userMapper.getById(id);
        UserQueryVO userQueryVO = new UserQueryVO();
        BeanUtils.copyProperties(user, userQueryVO);
        return userQueryVO;
    }

    /**
     * 修改当前用户的信息
     *
     * @param userModifyDTO
     */
    @Override
    public void modify(UserModifyDTO userModifyDTO) {
        Long currentUserId = BaseContext.getCurrentUserId();
        String username = userModifyDTO.getUsername();
        User originalUser = userMapper.getByUsername(username);

        //判断用户名是否被占用
        if (originalUser != null && !currentUserId.equals(originalUser.getId())) {
            throw new UsernameOccupiedException(MessageConstant.USERNAME_OCCUPIED);
        }

        User user = new User();
        BeanUtils.copyProperties(userModifyDTO, user);
        user.setId(currentUserId);
        userMapper.update(user);
    }
}
