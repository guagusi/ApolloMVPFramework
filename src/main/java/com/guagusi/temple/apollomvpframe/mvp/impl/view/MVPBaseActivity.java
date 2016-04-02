package com.guagusi.temple.apollomvpframe.mvp.impl.view;

import android.os.Bundle;
import android.view.View;

import com.guagusi.temple.apollomvpframe.base.view.BaseActivity;
import com.guagusi.temple.apollomvpframe.mvp.MVPPresenter;
import com.guagusi.temple.apollomvpframe.mvp.MVPView;


/**
 * Created by maozi on 2015/5/25.
 */
public abstract class MVPBaseActivity extends BaseActivity implements MVPView, View.OnClickListener {

    protected MVPPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPresenter();
        if(mPresenter != null) {
            mPresenter.attachMVPView(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mPresenter == null) {
            createPresenter();
            if(mPresenter != null) {
                mPresenter.attachMVPView(this);
            }
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
