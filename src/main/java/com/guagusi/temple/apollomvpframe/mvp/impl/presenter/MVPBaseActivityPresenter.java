package com.guagusi.temple.apollomvpframe.mvp.impl.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.guagusi.temple.apollomvpframe.base.net.BaseRequestEntity;
import com.guagusi.temple.apollomvpframe.base.net.BaseResponseEntity;
import com.guagusi.temple.apollomvpframe.mvp.MVPModel;
import com.guagusi.temple.apollomvpframe.mvp.MVPPresenter;
import com.guagusi.temple.apollomvpframe.mvp.MVPView;

import java.lang.ref.WeakReference;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class MVPBaseActivityPresenter implements MVPPresenter {
    private static final String TAG = MVPBaseActivityPresenter.class.getSimpleName();

    protected WeakReference<MVPView> mMvpViewRef;
    protected MVPModel mMvpModel;

    public MVPBaseActivityPresenter() {
        mMvpModel = createMVPModel();
    }

    @Override
    public void attachMVPView(MVPView view) {
        if(view == null) {
            throw new NullPointerException("MVPView view 为空");
        }
        if(mMvpViewRef == null) {
            mMvpViewRef = new WeakReference<>(view);
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
    public void sendRequest(Context context, BaseRequestEntity requestEntity,
                                     final int requestTag, Class clazz) {
        Log.e(TAG, mMvpModel == null ? "mMvpModel is null" : "not null");
        Log.e(TAG, mMvpViewRef == null ? "mMvpViewRef is null" : "not null");
        Log.e(TAG, mMvpViewRef.get() == null ? "mMvpViewRef.get() is null" : "not null");

        if(mMvpModel != null && mMvpViewRef.get() != null) {
            final MVPView mvpView = mMvpViewRef.get();
                mMvpModel.loadModel(context, requestEntity, new MVPModel.ModelCallback() {
                    @Override
                    public void onPreLoad(int pageIndex) {
                        Log.e(TAG, "activity presenter-onPreLoad");
                        mvpView.onOperateForResult(requestTag, 1, null);
                    }

                    @Override
                    public void onLoading(int pageIndex) {
                        Log.e(TAG, "activity presenter-onLoading");
                        mvpView.onOperateForResult(requestTag, 2, null);
                    }

                    @Override
                    public void onSuccess(int pageIndex, BaseResponseEntity entity) {
                        Log.e(TAG, "activity presenter-onSuccess");
                        Bundle data = new Bundle();
                        data.putSerializable("ResponseEntity", entity);
                        data.putInt("PageIndex", pageIndex);
                        mvpView.onOperateForResult(requestTag, 3, data);
                    }

                    @Override
                    public void onFailed(int pageIndex, BaseResponseEntity entity) {
                        Log.e(TAG, "activity presenter-onFailed");
                        Bundle data = new Bundle();
                        data.putSerializable("ResponseEntity", entity);
                        data.putInt("PageIndex", pageIndex);
                        mvpView.onOperateForResult(requestTag, 4, data);
                    }

                    @Override
                    public void onFinish(int pageIndex) {
                        Log.e(TAG, "activity presenter-onFinish");
                        mvpView.onOperateForResult(requestTag, 5, null);
                    }
            }, requestTag, clazz);
        }
    }
}

