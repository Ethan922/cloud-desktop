package edu.hdu.controller;

import edu.hdu.dto.ChangePasswordDTO;
import edu.hdu.dto.UserLoginDTO;
import edu.hdu.dto.UserSignupDTO;
import edu.hdu.result.Result;
import edu.hdu.service.UserService;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserSignupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/signup")
    @ApiOperation("用户注册")
    public Result<UserSignupVO> signup(@RequestBody UserSignupDTO userSignupDTO) {
        log.info("用户注册");
        UserSignupVO userSignupVO = userService.signup(userSignupDTO);
        return Result.success(userSignupVO);
    }

    @DeleteMapping("/logoff/{id}")
    @ApiOperation("注销账户用户")
    public Result logoff(@PathVariable Long id){
        log.info("注销账户，id:"+id);
        userService.logoff(id);
        return Result.success();
    }

    @PutMapping("/changePassword")
    @ApiOperation("修改密码")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        log.info("修改密码");
        userService.changePassword(changePasswordDTO);
        return Result.success();
    }

    @PostMapping("/changeActiveness/{id}")
    @ApiOperation("禁用或启用用户账号")
    public Result changeActiveness(@PathVariable Long id){
        log.info("禁用或启用用户账号，用户id:"+id);
        userService.changeActiveness(id);
        return Result.success();
    }
}
