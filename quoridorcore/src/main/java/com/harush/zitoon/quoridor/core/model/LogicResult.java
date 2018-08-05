package com.harush.zitoon.quoridor.core.model;

public class LogicResult {

    private boolean success;

    private String errMsg;

    public LogicResult(boolean success) {
        this.success = success;
    }

    public LogicResult(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
