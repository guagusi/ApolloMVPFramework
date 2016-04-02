package com.guagusi.temple.apollomvpframe.base.net;

import com.omega.app.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maozi on 2015/5/25.
 */
public class BaseResponseEntity<D> implements Serializable {

    protected int code;                // 状态码
    protected boolean isSuccess;    // 是否请求成功
    protected String msg;      // 提示消息
    protected D data;       // 返回数据
    protected int count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
