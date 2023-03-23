package com.rental.premisesrental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName
@ApiModel(value = "用户信息")
public class User {

    @TableId(value = "id")
    @ApiModelProperty("用户主键")
    private Integer id;
    private String username;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String phone;
}
