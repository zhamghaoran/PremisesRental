package com.rental.premisesrental.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回的场地数据")
public class PlaceDTO {
    @ApiModelProperty(value = "场地类型",required = true)
    private String type;

    @ApiModelProperty(value = "场地单价",required = true)
    private Integer cost;

    @ApiModelProperty(value = "空闲情况")
    private List<List<Integer>> available;
}
