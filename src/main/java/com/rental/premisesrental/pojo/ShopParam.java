package com.rental.premisesrental.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@ApiModel(value = "返回商户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopParam {
    @TableId(value = "id")
    @ApiModelProperty(value = "商铺主键")
    private String id;

    @ApiModelProperty(value = "地址",required = true)
    private String location;

    @ApiModelProperty(value = "绑定用户id")
    private String ownerId;
    private Timestamp createTime;
    private Timestamp updateTime;
}
