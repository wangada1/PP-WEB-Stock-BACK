package com.example.ppback.service;
/*
 *  Copyright (c) 2017 Robert Bosch GmbH & Bosch Software Innovations GmbH, Germany. All rights reserved.
 *
 */

public enum HttpsResponseEnum {

    SUCCESS(1000, "成功"),
    UPLOAD_FAILED(8001, "上传出现异常，请稍后再试"),
    NO_SELECTED_FAILED(8002, "上传失败，请选择文件"),
    FILE_SUFFIX_INCORRECT_FAILED(8003, "上传失败，文件格式不正确"),
    FAILED(9999, "失败"),
    INVALID_FILE_TYPE(9003, "文件格式不正确"),
    LAST_MONTH_NONEXIST(8004, "上月信息不存在"),
    THIS_MONTH_NONEXIST(8005, "本月信息不存在");
    // LEAD_CANNOT_DELETE(9017, "选中的lead中包含状态不为待分配或来自robin系统的，无法删除");

    private int responseCode;
    private String responseMSG;

    private HttpsResponseEnum(int responseCode, String responseMSG) {
        this.responseCode = responseCode;
        this.responseMSG = responseMSG;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMSG() {
        return responseMSG;
    }
}
