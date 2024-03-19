package edu.hdu.service;


import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;

public interface RoleService {
    Role createRole(RoleDTO roleDTO);

    void delete(Long id);
}
