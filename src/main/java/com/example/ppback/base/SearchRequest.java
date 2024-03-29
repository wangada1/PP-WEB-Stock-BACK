package com.example.ppback.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "search condition for market")
public class SearchRequest {

    @ApiModelProperty(value = "group", position = 1)
    private String group;

    @ApiModelProperty(value = "monthYear", position = 2)
    private String monthYear;

}
