package com.example.ppback.service;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class BaseHttpResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filePath;

    @ApiModelProperty(value = "响应结果code")
    int status;
    @ApiModelProperty(value = "响应结果message")
    String message;
    @ApiModelProperty(value = "返回的消息体")
    T body;

    public BaseHttpResponse() {
        setSuccess();
    }

    public BaseHttpResponse(T body) {
        this.body = body;
    }


    public void setSuccess() {
        status = HttpsResponseEnum.SUCCESS.getResponseCode();
        message = HttpsResponseEnum.SUCCESS.getResponseMSG();
    }

    public void setSuccess(T body) {
        setSuccess();
        this.setBody(body);
    }


    public void setFailed() {
        status = HttpsResponseEnum.FAILED.getResponseCode();
        message = HttpsResponseEnum.FAILED.getResponseMSG();
    }

    public void setFailed(HttpsResponseEnum failType) {
        status = failType.getResponseCode();
        message = failType.getResponseMSG();
    }

    public boolean isSuccess(){
        return status == HttpsResponseEnum.SUCCESS.getResponseCode();
    }
}
