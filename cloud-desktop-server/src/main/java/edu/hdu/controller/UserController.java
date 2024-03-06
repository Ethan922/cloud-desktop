package edu.hdu.controller;

import edu.hdu.constant.JwtClaimsConstant;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.properties.JwtProperties;
import edu.hdu.result.Result;
import edu.hdu.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@Api("用户相关接口")
public class UserController {
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    // TODO: 2024/2/26 登录
    public Result<String> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录...");
        if (userLoginDTO.getUsername().equals("admin")&&userLoginDTO.getPassword().equals("123456")){
            //登录成功后，生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, 1);
            String token = JwtUtil.createJWT(
                    jwtProperties.getSecretKey(),
                    jwtProperties.getTtl(),
                    claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }

}
