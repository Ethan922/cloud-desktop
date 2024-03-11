package edu.hdu.mapper;

import edu.hdu.entity.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RolesMapper {
    @Select("select * from roles where name=#{name};")
    Roles getByName(String name);
}
