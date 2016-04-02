package com.guagusi.temple.apollomvpframe.mvp.impl.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.omega.frame.base.net.BaseRequestEntity;
import com.omega.frame.base.net.BaseResponseEntity;
import com.omega.frame.mvp.MVPModel;
import com.omega.frame.mvp.MVPPresenter;
import com.omega.frame.mvp.MVPView;

import java.lang.ref.WeakReference;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class MVPBaseFragmentPresenter implements MVPPresenter {

    protected WeakReference<MVPView> mMvpViewRef;
    protected MVPModel mMvpModel;

    public MVPBaseFragmentPresenter() {
        mMvpModel = createMVPModel();
    }

    @Override
    public void attachMVPView(MVPView view) {
        if(view == null) {
            throw new NullPointerException("MVPView view 为空");
        }
        if(mMvpViewRef == null) {
            mMvpViewRef = new WeakReference(view);
        }

    }

    @Override
    public void detachMVPView() {
        if(mMvpViewRef != null) {
            mMvpViewRef = null;
        }
    }

    @Override
    public void destroydMVPModel() {
        mMvpModel = null;
    }

    /**
     * 子类实现。MVPView调用发请求
     * @param context
     * @param requestEntity
     * @param requestTag 区分请求接口
     */
    @Override
    public void sendRequest(Context context, BaseRequestEntity requestEntity, final int requestTag, Class clazz) {
        if(mMvpModel != null && mMvpViewRef.get() != null) {
            final MVPView mvpView = mMvpViewRef.get();
                mMvpModel.loadModel(context, requestEntity, new MVPModel.ModelCallback() {
                    @Override
                    public void onPreLoad(int pageIndex) {
                        Log.e("==============================", "presenter-onPreLoad");
                        mvpView.onOperateForResult(requestTag, 1, null);
                    }

                    @Override
                    public void onLoading(int pageIndex) {
                        Log.e("==============================", "presenter-onLoading");
                        mvpView.onOperateForResult(requestTag, 2, null);
                    }

                    @Override
                    public void onSuccess(int pageIndex, BaseResponseEntity entity) {
                        Log.e("==============================", "presenter-onSuccess");
                        Bundle data = new Bundle();
                        data.putSerializable("ResponseEntity", entity);
                        data.putInt("PageIndex", pageIndex);
                        mvpView.onOperateForResult(requestTag, 3, data);
                    }

                    @Override
                    public void onFailed(int pageIndex, BaseResponseEntity entity) {
                        Log.e("==============================", "presenter-onFailed");
                        Bundle data = new Bundle();
                        data.putSerializable("ResponseEntity", entity);
                        data.putInt("PageIndex", pageIndex);
                        mvpView.onOperateForResult(requestTag, 4, data);
                    }

                    @Override
                    public void onFinish(int pageIndex) {
                        Log.e("==============================", "presenter-onFinish");
                        mvpView.onOperateForResult(requestTag, 5, null);
                    }
                }, requestTag, clazz);
            }
        }
}
