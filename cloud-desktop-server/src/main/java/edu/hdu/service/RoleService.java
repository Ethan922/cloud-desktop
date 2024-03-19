package edu.hdu.service;


import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;
import edu.hdu.vo.RoleVO;

public interface RoleService {
    RoleVO createRole(RoleDTO roleDTO);

    void delete(Long id);
}
