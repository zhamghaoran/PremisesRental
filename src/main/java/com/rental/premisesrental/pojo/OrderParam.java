package com.rental.premisesrental.pojo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderParam {
    @ApiModelProperty(value = "商铺id")
    Long shopId;

    @ApiModelProperty(value = "场地id")
    Long placeId;

    @ApiModelProperty(value = "时间偏移量")
    Integer dayOffSet;

    @ApiModelProperty(value = "开始时间")
    Integer beginTime;

    @ApiModelProperty(value = "租赁时间")
    Integer rentTime;
}
