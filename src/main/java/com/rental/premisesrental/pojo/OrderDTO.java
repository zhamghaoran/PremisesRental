package com.rental.premisesrental.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@ApiModel(value = "订单返回数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @ApiModelProperty(value = "店铺id")
    private Long orderId;
    @ApiModelProperty(value = "商铺id")
    private Long shopId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "租赁时间")
    private Integer rentTime;
    @ApiModelProperty(value = "下单时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "实际消费时间")
    private Timestamp certainTime;
}
