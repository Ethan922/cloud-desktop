package edu.hdu.mapper;

import edu.hdu.entity.UserRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRolesMapper {
    @Select("select * from user_roles where user_id=#{userId};")
    UserRoles getByUserId(Long userId);
}
