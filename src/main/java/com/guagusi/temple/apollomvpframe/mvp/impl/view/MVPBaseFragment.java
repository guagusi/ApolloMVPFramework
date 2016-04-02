package com.guagusi.temple.apollomvpframe.mvp.impl.view;

import android.os.Bundle;

import com.guagusi.temple.apollomvpframe.base.view.BaseFragment;
import com.guagusi.temple.apollomvpframe.mvp.MVPPresenter;
import com.guagusi.temple.apollomvpframe.mvp.MVPView;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class MVPBaseFragment extends BaseFragment implements MVPView {

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
    public void onResume() {
        super.onResume();
        if(mPresenter == null) {
            createPresenter();
            if(mPresenter != null) {
                mPresenter.attachMVPView(this);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mPresenter != null) {
            mPresenter.detachMVPView();
        }
    }
}
