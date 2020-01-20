package com.jclz.fruit.constant;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public enum GenResult {
    SUCCESS(1000, "请求成功"),

    FAILED(1001, "请求失败或暂无数据"),

    LOGIN_SUCCESS(1002, "登录成功"),

    PARAMS_ERROR(1003, "请求参数错误"),

    NOT_LOGIN(1004, "未登录"),

    PASSWORD_ERROR(1005, "密码错误!"),

    USER_LOCKED_ERROR(1006, "登录失败，该用户已被冻结!"),

    USER_NOT_FOND(1006, "该用户不存在!"),

    NO_DATA_OR_INVALID(1007, "数据不存在或已过期，请刷新重试!"),

    UNKNOWN_ERROR(9999, "未知异常"),

    QiniuYun_Error(10000, "七牛云传文件异常"),

    BroadCast_Error(10001, "广播异常");

    public int msgCode;
    public String message;

    private GenResult(int msgCode, String message) {
        this.msgCode = msgCode;
        this.message = message;
    }

    public Map<String, Object> genResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        return map;
    }

    public Map<String, Object> genResult(Object data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        map.put("data", data);
        return map;
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        object.put("code", msgCode);
        object.put("msg", message);
        return object.toString();
    }
}
