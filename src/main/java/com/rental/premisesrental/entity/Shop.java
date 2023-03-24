package com.rental.premisesrental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinitions;
import java.sql.Timestamp;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商铺信息")
public class Shop {
    @TableId(value = "id")
    @ApiModelProperty(value = "商铺主键")
    private Long id;
    @ApiModelProperty(value = "地址")
    private String location;

    private Timestamp createTime;
    private Timestamp updateTime;
}

