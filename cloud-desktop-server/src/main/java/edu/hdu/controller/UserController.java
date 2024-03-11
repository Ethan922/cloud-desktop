package edu.hdu.controller;

import edu.hdu.constant.JwtClaimsConstant;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.dto.UserSignupDTO;
import edu.hdu.entity.User;
import edu.hdu.exception.PasswordErrorException;
import edu.hdu.properties.JwtProperties;
import edu.hdu.result.Result;
import edu.hdu.service.UserService;
import edu.hdu.utils.JwtUtil;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserSignupVO;
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
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录...");
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }

}
