package com.rental.premisesrental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "场地信息")
public class Place {
    @ApiModelProperty(value = "场地主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "场地类型",required = true)
    private String type;

    @ApiModelProperty(value = "场地单价",required = true)
    private Integer cost;

    @ApiModelProperty(value = "空闲情况")
    private String available;
    @ApiModelProperty(value = "商铺ID",required = true)
    private Long shopId;

    private Timestamp createTime;
    private Timestamp updateTime;

}
