package com.guagusi.temple.apollomvpframe.base.net;

import android.text.TextUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by maozi on 2015/5/31.
 */
public class BaseRequestEntity {

    protected Map<String, String> mParams = new HashMap<String, String>();

    public static final BaseRequestEntity newInstance() {
        return new BaseRequestEntity();
    }

    /**
     * 添加参数
     * @param key
     * @param value
     */
    public BaseRequestEntity addParam(String key, String value) {
        if(!TextUtils.isEmpty(key)) {
            mParams.put(key, value);
        }
        return this;
    }

    /**
     * 通用参数
     * @return
     */
    public BaseRequestEntity addCommonParas(Map<String, String> commonParams) {
        if(commonParams != null && !commonParams.isEmpty()) {
            Iterator iterator = commonParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if(!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)) {
                    mParams.put(key, value);
                }
            }
        }
        return this;
    }

    public Map<String, String> getParams() {
        return mParams;
    }
}
