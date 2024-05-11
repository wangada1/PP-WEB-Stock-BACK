package com.example.ppback.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "delete the data by month and the class")
public class DeleteRequest {

    @ApiModelProperty(value = "selectedmonth", position = 1)
    private String selectedmonth;

}
