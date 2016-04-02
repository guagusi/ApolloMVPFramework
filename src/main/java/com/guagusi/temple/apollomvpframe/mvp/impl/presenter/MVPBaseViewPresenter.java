package com.guagusi.temple.apollomvpframe.mvp.impl.presenter;

import com.guagusi.temple.apollomvpframe.mvp.MVPModel;
import com.guagusi.temple.apollomvpframe.mvp.MVPPresenter;
import com.guagusi.temple.apollomvpframe.mvp.MVPView;

import java.lang.ref.WeakReference;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class MVPBaseViewPresenter implements MVPPresenter {

    protected WeakReference<MVPView> mMvpViewRef;
    protected MVPModel mMvpModel;

    public MVPBaseViewPresenter() {
        mMvpModel = createMVPModel();
    }

    @Override
    public void attachMVPView(MVPView view) {
        if(view == null) {
            throw new NullPointerException("MVPView view 为空");
        }
        if(mMvpViewRef == null) {
            mMvpViewRef = new WeakReference<MVPView>(view);
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

}
