package edu.hdu.controller;

import edu.hdu.context.BaseContext;
import edu.hdu.dto.*;
import edu.hdu.result.PageResult;
import edu.hdu.result.Result;
import edu.hdu.service.UserService;
import edu.hdu.vo.UserLoginVO;
import edu.hdu.vo.UserQueryVO;
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
    public Result logoff(@PathVariable Long id) {
        log.info("注销账户，id:" + id);
        userService.logoff(id);
        return Result.success();
    }

    @PutMapping("/changePassword")
    @ApiOperation("修改密码")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        log.info("修改密码");
        userService.changePassword(changePasswordDTO);
        return Result.success();
    }

    @PostMapping("/changeActiveness/{id}")
    @ApiOperation("禁用或启用用户账号")
    public Result changeActiveness(@PathVariable Long id) {
        log.info("禁用或启用用户账号，用户id:" + id);
        userService.changeActiveness(id);
        return Result.success();
    }


    @GetMapping("/pageQuery")
    @ApiOperation("用户分页查询")
    public Result<PageResult> pageQuery(UserPageQueryDTO userPageQueryDTO) {
        log.info("用户分页查询");
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/detail")
    @ApiOperation("查询当前用户详细信息")
    public Result<UserQueryVO> getById() {
        log.info("查询当前用户详细信息,用户id:" + BaseContext.getCurrentUserId());
        UserQueryVO userQueryVO = userService.getById(BaseContext.getCurrentUserId());
        return Result.success(userQueryVO);
    }

    @PutMapping("/modify")
    @ApiOperation("修改当前用户的信息")
    public Result modify(@RequestBody UserModifyDTO userModifyDTO) {
        log.info("修改当前用户信息,用户id:" + BaseContext.getCurrentUserId());
        userService.modify(userModifyDTO);
        return Result.success();
    }
}
