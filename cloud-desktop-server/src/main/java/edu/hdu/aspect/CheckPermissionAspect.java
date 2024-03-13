package edu.hdu.aspect;

import edu.hdu.constant.MessageConstant;
import edu.hdu.constant.PermissionConstant;
import edu.hdu.context.BaseContext;
import edu.hdu.entity.UserRole;
import edu.hdu.exception.PermissionDeniedException;
import edu.hdu.mapper.UserMapper;
import edu.hdu.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 校验用户权限
 */
@Aspect
@Component
@Slf4j
public class CheckPermissionAspect {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Pointcut("execution(* edu.hdu.service.Impl.*.*(..)) && @annotation(edu.hdu.annotation.CheckPermission)")
    public void checkPermissionPointcut(){}

    @Before("checkPermissionPointcut()")
    public void checkPermission(){
        Long currentUserId = BaseContext.getCurrentUserId();
        UserRole userRole = userRoleMapper.getByUserId(currentUserId);
        if(!Objects.equals(userRole.getPermission(), PermissionConstant.ADMINSTRATOR)){
            throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        }
    }

}
