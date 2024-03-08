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
public class Image {

    private Long id;

    private String imageId;

    private String image;

    private String name;

    private String tag;

    private String size;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;

    private Boolean isActive;

    private Long ownerId;
}
