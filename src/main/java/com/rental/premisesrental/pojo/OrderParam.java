package com.rental.premisesrental.pojo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderParam {
    @ApiParam(value = "开始时间")
    private Long beginTime;
    @ApiParam(value = "租赁时间")
    private Long rentTime;
    @ApiParam(value = "场地id")
    private Long placeId;
}
