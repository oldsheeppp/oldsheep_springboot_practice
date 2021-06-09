package com.oldsheep.response;

public class RetResponse {

    public static <T> RetResult<T> makeOKRsp(String Msg, boolean Success, T data) {
        RetResult<T> result = new RetResult<>();
        result.setCode(200);
        result.setMsg(Msg);
        result.setSuccess(Success);
        result.setData(data);
        return result;
    }

    public static <T> RetResult<T> makeErrRsp(String Msg, boolean Success, T data) {
        RetResult<T> result = new RetResult<>();
        result.setCode(400);
        result.setMsg(Msg);
        result.setSuccess(Success);
        result.setData(data);
        return result;
    }

    public static <T> RetResult<T> makeRsp(int code, String Msg, boolean Success, T data) {
        RetResult<T> result = new RetResult<>();
        result.setCode(code);
        result.setMsg(Msg);
        result.setSuccess(Success);
        result.setData(data);
        return result;
    }
}
