package edu.hdu.service.Impl;

import edu.hdu.annotation.CheckPermission;
import edu.hdu.constant.MessageConstant;
import edu.hdu.constant.RolesConstant;
import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;
import edu.hdu.entity.UserRole;
import edu.hdu.exception.RoleDeleteFailedException;
import edu.hdu.exception.RoleNameOccupiedException;
import edu.hdu.mapper.RoleMapper;
import edu.hdu.mapper.UserRoleMapper;
import edu.hdu.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 创建角色
     *
     * @param roleDTO
     * @return
     */
    @Override
    @CheckPermission
    public Role createRole(RoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();
        Role role = roleMapper.getByRoleName(roleName);

        //角色名已存在
        if (role != null) {
            throw new RoleNameOccupiedException(MessageConstant.ROLE_NAME_OCCUPIED);
        }

        //插入角色
        role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleMapper.insert(role);

        return role;
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @CheckPermission
    @Override
    public void delete(Long id) {

        Role role=roleMapper.getById(id);
        //禁止删除两个默认角色，adminstrator、user
        if (role==null){
            throw new RoleDeleteFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        String roleName=role.getRoleName();
        if (RolesConstant.ADMINSTRATOR.equals(roleName)||RolesConstant.USER.equals(roleName)){
            throw new RoleDeleteFailedException(MessageConstant.ROLE_DELETE_NOT_ALLOWED);
        }

        UserRole userRole = userRoleMapper.getByRoleId(id);
        //当前角色关联了用户。无法删除
        if (userRole!=null){
            throw new RoleDeleteFailedException(MessageConstant.ROLE_ASSOCIATE_USER);
        }
        
        roleMapper.delete(id);

    }

}
