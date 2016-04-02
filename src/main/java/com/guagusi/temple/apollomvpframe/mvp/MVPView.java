package com.guagusi.temple.apollomvpframe.mvp;

import android.os.Bundle;

/**
 * 提供类型
 * Created by maozi on 2015/5/21.
 */
public interface MVPView {

    /**
     * 在onCreate中调用
     * 实例化与此View 关联的Presenter。如果是多个这实例化多个
     */
    void createPresenter();

    /**
     * 根据View的状态调用Presenter然后在调用者回调
     * e.g. onResume() onPause()。。。,网络加载,加载失败...
     * @param viewState
     */
    void onViewStateForResult(int viewState, Bundle data);

    /**
     * 各种点击滑动事件回调
     * @param requestCode
     * @param resultCode
     */
    void onOperateForResult(int requestCode, int resultCode, Bundle data);
}
