package edu.hdu.mapper;

import edu.hdu.entity.Container;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContainerMapper {
    @Delete("delete from container where user_id=#{userId};")
    void deleteByUserId(Long userId);

    @Select("select * from container where user_id=#{userId};")
    List<Container> getByUserId(Long userId);
}
