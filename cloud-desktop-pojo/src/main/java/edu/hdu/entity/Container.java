package edu.hdu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Container {

    private Long id;

    private String containerId;

    private Long hostPort;

    private Long imageId;

    private String name;

    private String image;

    private Long ownerId;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;

    private Boolean isActive;

    private Integer status;
}
