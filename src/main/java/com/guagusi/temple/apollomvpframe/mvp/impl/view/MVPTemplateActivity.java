package com.guagusi.temple.apollomvpframe.mvp.impl.view;

import android.os.Bundle;
import android.view.View;

import com.guagusi.temple.apollomvpframe.base.view.TemplateActivity;
import com.guagusi.temple.apollomvpframe.mvp.MVPPresenter;
import com.guagusi.temple.apollomvpframe.mvp.MVPView;

/**
 * Created by maozi on 2015/6/5.
 */
public abstract class MVPTemplateActivity extends TemplateActivity implements MVPView {

    protected MVPPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPresenter();
        if(mPresenter != null) {
            mPresenter.attachMVPView(this);
        }

        sendRequest(true);
    }

    @Override
    public void onViewStateForResult(int viewState, Bundle data) {

    }

    @Override
    public void onOperateForResult(int requestCode, int resultCode, Bundle data) {
        // 基本处理。不区分 requestCode。由子类实现相关回调实现区分
        switch (resultCode) {
            case 1:
                // pre loading
                break;
            case 2:
                // loading
                mLoading = true;
                if(!mSRLLayout.isRefreshing()) {
                    mPBLoading.setVisibility(View.VISIBLE);
                    mTVHint.setVisibility(View.GONE);
                }
                break;
            case 3:
                mPBLoading.setVisibility(View.GONE);
                mTVHint.setVisibility(View.GONE);
                // 具体处理由子类实现
                onRequestSuccess(requestCode, resultCode, data);
                break;
            case 4:
                mPBLoading.setVisibility(View.GONE);
                mTVHint.setVisibility(View.VISIBLE);
                // 具体处理由子类实现
                onRequestFailed(requestCode, resultCode, data);
            case 5:
                mLoading = false;
                if (mSRLLayout.isRefreshing()) {
                    mSRLLayout.setRefreshing(mLoading);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPresenter != null) {
            mPresenter.detachMVPView();
        }
    }
}
