package com.guagusi.temple.apollomvpframe.mvp;

import android.content.Context;
import com.guagusi.temple.apollomvpframe.base.net.BaseRequestEntity;
import com.guagusi.temple.apollomvpframe.base.net.BaseResponseEntity;

import java.io.Serializable;

/**
 * Created by maozi on 2015/5/25.
 */
public interface MVPModel extends Serializable {

    /**
     * 加载数据
     * @param requestEntity 请求参数
     * @param modelCallback
     * @param requestTag 区分请求
     * @return
     */
    MVPModel loadModel(Context context, BaseRequestEntity requestEntity, ModelCallback modelCallback, int requestTag, Class clazz);

    /**
     * 分页加载数据
     * @param requestEntity 请求参数
     * @param pageIndex
     * @param modelCallback
     * @param requestTag 区分请求
     * @return
     */
    MVPModel loadModelByPage(Context context, BaseRequestEntity requestEntity, int pageIndex, ModelCallback modelCallback, int requestTag);

    /**
     * MVPModel加载数据回调
     */
    interface ModelCallback {

        /**
         * 加载数据前执行一些相关操作
         */
        void onPreLoad(int pageIndex);

        /**
         * 加载中
         */
        void onLoading(int pageIndex);

        /**
         * 加载成功
         */
        void onSuccess(int pageIndex, BaseResponseEntity entity);

        /**
         * 加载失败
         */
        void onFailed(int pageIndex, BaseResponseEntity entity);

        /**
         * 加载完成
         */
        void onFinish(int pageIndex);
    }
}
