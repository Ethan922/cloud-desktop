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
@ApiModel("用户视图模型")
public class UserVO {

    @ApiModelProperty("用户ID")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("用户状态")
    private Boolean isActive;

    @ApiModelProperty("用户角色名")
    private String roleName;

    @ApiModelProperty("用户权限")
    private Integer permission;
}
