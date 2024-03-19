package edu.hdu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("添加角色时传递的数据模型")
public class RoleDTO {

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色权限")
    private Integer permission;
}
