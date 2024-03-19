package edu.hdu.mapper;

import edu.hdu.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    @Select("select * from role where role_name=#{roleName};")
    Role getByRoleName(String roleName);

    void insert(Role role);

    @Delete("delete from role where id=#{id};")
    void delete(Long id);

    @Select("select * from role where id=#{id};")
    Role getById(Long id);
}
