package com.rental.premisesrental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rental.premisesrental.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Sort;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
