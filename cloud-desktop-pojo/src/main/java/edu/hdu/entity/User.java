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
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
}
