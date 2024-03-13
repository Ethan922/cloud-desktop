package edu.hdu.mapper;

import com.github.pagehelper.Page;
import edu.hdu.dto.UserPageQueryDTO;
import edu.hdu.entity.User;
import edu.hdu.result.PageResult;
import edu.hdu.vo.UserVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username};")
    User getByUsername(String username);

    void insert(User user);

    void update(User user);

    @Delete("delete from user where id=#{id};")
    void delete(Long id);

    @Select("select * from user where id=#{id};")
    User getById(Long id);

    Page<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO);
}
