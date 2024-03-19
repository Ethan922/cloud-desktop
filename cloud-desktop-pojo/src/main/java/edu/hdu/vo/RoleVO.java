package edu.hdu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色视图模型")
public class RoleVO {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色权限")
    private Integer permission;

}
