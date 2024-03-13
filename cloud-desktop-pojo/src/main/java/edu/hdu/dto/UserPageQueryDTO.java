package edu.hdu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户分页查询时传递的数据模型")
public class UserPageQueryDTO implements Serializable {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户账号状态")
    private Boolean isActive;

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页记录数")
    private int pageSize;
}
