package edu.hdu.mapper;

import edu.hdu.entity.Roles;
import edu.hdu.entity.User;
import edu.hdu.entity.UserRoles;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRolesMapper {
    @Select("select * from user_roles where user_id=#{userId};")
    UserRoles getByUserId(Long userId);

    @Insert("insert into user_roles (user_id, role_id, name, permissions) " +
            "values (#{userId},#{roleId},#{name},#{permissions});")
    void insert(UserRoles userRoles);
}
