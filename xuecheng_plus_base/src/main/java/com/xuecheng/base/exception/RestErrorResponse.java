package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/15 15:16
 * @description 与前端约定返回的异常信息模型
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

