package edu.hdu.service.Impl;

import edu.hdu.annotation.CheckPermission;
import edu.hdu.constant.MessageConstant;
import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;
import edu.hdu.exception.RoleNameOccupiedException;
import edu.hdu.mapper.RoleMapper;
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

    /**
     * 创建角色
     * @param roleDTO
     * @return
     */
    @Override
    @CheckPermission
    public Role createRole(RoleDTO roleDTO) {
        String roleName=roleDTO.getRoleName();
        Role role=roleMapper.getByRoleName(roleName);

        //角色名已存在
        if (role!=null){
            throw new RoleNameOccupiedException(MessageConstant.ROLE_NAME_OCCUPIED);
        }

        //插入角色
        role=new Role();
        BeanUtils.copyProperties(roleDTO,role);
        roleMapper.insert(role);

        return role;
    }
}
