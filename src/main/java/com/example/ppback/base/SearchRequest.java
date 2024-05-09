package com.example.ppback.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "search condition for market")
public class SearchRequest {

    @ApiModelProperty(value = "pdcl", position = 1)
    private String pdcl;

    @ApiModelProperty(value = "monthYear", position = 2)
    private String monthYear;

    @ApiModelProperty(value = "vendor", position = 3)
    private String vendor;
    
    @ApiModelProperty(value = "type", position = 4)
    private String type;
    
    

}
