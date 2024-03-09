package edu.hdu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {

    private Long id;

    private Long userId;

    private Long roleId;

    private String name;

    private Integer permissions;

}
