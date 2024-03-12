package edu.hdu.mapper;

import edu.hdu.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper {
    @Select("select * from user_role where user_id=#{userId};")
    UserRole getByUserId(Long userId);

    @Insert("insert into user_role (user_id, role_id, name, permission) " +
            "values (#{userId},#{roleId},#{name},#{permission});")
    void insert(UserRole userRole);

    @Delete("delete from user_role where user_id=#{userId};")
    void deleteByUserId(Long userId);
}
