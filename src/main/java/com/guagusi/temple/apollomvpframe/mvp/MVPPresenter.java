package com.guagusi.temple.apollomvpframe.mvp;

import android.content.Context;

import com.guagusi.temple.apollomvpframe.base.net.BaseRequestEntity;

/**
 * Created by maozi on 2015/5/21.
 */
public interface MVPPresenter {

    /**
     * 绑定View
     * @param view
     */
    void attachMVPView(MVPView view);

    /**
     * view 被销毁的时候调用。e.g. Activity.detachView() Fragment.onDestroyView()
     */
    void detachMVPView();

    /**
     * 为presenter 绑定Model
     */
    MVPModel createMVPModel();

    /**
     * view 被销毁的时候调用
     */
    void destroydMVPModel();

    /**
     * 子类实现。MVPView调用发请求
     * @param context
     * @param requestEntity
     * @param requestTag 区分请求接口
     */
    void sendRequest(Context context, BaseRequestEntity requestEntity, final int requestTag, Class clazz);


}
