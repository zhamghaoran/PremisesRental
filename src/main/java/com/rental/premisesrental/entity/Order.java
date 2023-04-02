package com.rental.premisesrental.entity;

import cn.hutool.core.lang.ParameterizedTypeImpl;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单")
@TableName(value = "orderTable")
public class Order {
    @ApiModelProperty(value = "订单号")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "场地id")
    private Long placeId;

    @ApiModelProperty(value = "开始时间")
    private Timestamp beginTime;

    @ApiModelProperty(value = "租赁时间")
    private Integer rentTime;

    @ApiModelProperty(value = "订单创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "是否被删除")
    private Integer isDeleted;

    public Order( Long userId,Long shopId, Long placeId, Timestamp beginRentTime, Integer rentTime, Timestamp timestamp) {
        this.userId = userId;
        this.shopId = shopId;
        this.placeId = placeId;
        this.beginTime = beginRentTime;
        this.rentTime = rentTime;
        this.createTime = timestamp;
    }
}
