package edu.hdu.mapper;

import edu.hdu.dto.UserSignupDTO;
import edu.hdu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username};")
    User getByUsername(String username);

    void insert(User user);
}
