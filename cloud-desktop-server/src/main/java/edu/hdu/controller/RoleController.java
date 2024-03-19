package edu.hdu.controller;

import edu.hdu.dto.RoleDTO;
import edu.hdu.entity.Role;
import edu.hdu.result.Result;
import edu.hdu.service.RoleService;
import edu.hdu.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理接口")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/create")
    @ApiOperation("添加角色")
    public Result<RoleVO> createRole(@RequestBody RoleDTO roleDTO){
        log.info("新增角色，角色名:"+roleDTO.getRoleName());
        RoleVO roleVO =roleService.createRole(roleDTO);
        return Result.success(roleVO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除角色")
    public Result deleteRole(@PathVariable Long id){
        log.info("删除角色，角色id:"+id);
        roleService.delete(id);
        return Result.success();
    }
    
}
