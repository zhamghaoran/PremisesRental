package com.rental.premisesrental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rental.premisesrental.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.map.repository.config.EnableMapRepositories;

/**
 * @author 20179
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
