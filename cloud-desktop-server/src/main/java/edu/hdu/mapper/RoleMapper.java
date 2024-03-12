package edu.hdu.mapper;

import edu.hdu.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    @Select("select * from role where name=#{name};")
    Role getByName(String name);
}