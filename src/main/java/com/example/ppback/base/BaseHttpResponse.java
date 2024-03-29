package com.example.ppback.base;

import com.example.ppback.base.HttpResponseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseHttpResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

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
        status = HttpResponseEnum.SUCCESS.getResponseCode();
        message = HttpResponseEnum.SUCCESS.getResponseMSG();
    }

    public void setSuccess(T body) {
        setSuccess();
        this.setBody(body);
    }


    public void setFailed() {
        status = HttpResponseEnum.FAILED.getResponseCode();
        message = HttpResponseEnum.FAILED.getResponseMSG();
    }

    public void setFailed(HttpResponseEnum failType) {
        status = failType.getResponseCode();
        message = failType.getResponseMSG();
    }

    public boolean isSuccess(){
        return status == HttpResponseEnum.SUCCESS.getResponseCode();
    }
}
