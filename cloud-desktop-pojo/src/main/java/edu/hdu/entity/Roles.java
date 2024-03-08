package edu.hdu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    private Long id;

    //角色名
    private  String name;

    //用户权限
    private Integer permissions;
}
