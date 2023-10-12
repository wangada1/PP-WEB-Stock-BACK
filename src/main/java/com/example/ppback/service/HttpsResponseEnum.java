package com.example.ppback.service;

public enum HttpsResponseEnum {

    SUCCESS(1000, "成功"),

    CONTAINER_GET_FAILED(8000, "获取容器失败"),
    UPLOAD_FAILED(8001, "上传出现异常，请稍后再试"),
    NO_SELECTED_FAILED(8002, "上传失败，请选择文件"),
    FILE_SUFFIX_INCORRECT_FAILED(8003, "上传失败，文件格式不正确"),

    FAILED(9999, "失败"),
    INVALID_TOKEN(9000, "Invalid api token"),
    UNKNOWN_USER_OR_PASSWORD(9001, "UNKNOWN_USER_OR_PASSWORD"),
    INVALID_FILE_TYPE(9003, "文件格式不正确"),
  
    UNKNOWN_FILE_TYPE(9012, "未知文件類型"),
    OPERATION_TOO_FREQUENT(9013, "操作過於頻繁，請稍後再試"),
    SAVE_FAILED(9020, "添加操作失败"),;
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
