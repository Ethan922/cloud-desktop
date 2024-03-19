package edu.hdu.controller;

import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;
import edu.hdu.mapper.RoleMapper;
import edu.hdu.result.Result;
import edu.hdu.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理接口")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/create")
    @ApiOperation("添加角色")
    public Result<Role> createRole(@RequestBody RoleDTO roleDTO){
        log.info("新增角色，角色名:"+roleDTO.getRoleName());
        Role role =roleService.createRole(roleDTO);
        return Result.success(role);
    }
}
