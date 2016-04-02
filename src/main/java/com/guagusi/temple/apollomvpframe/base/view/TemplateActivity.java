package com.guagusi.temple.apollomvpframe.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guagusi.temple.apollomvpframe.R;

/**
 * 具备Toolbar，失败点击重新加载
 * Created by maozi on 2015/6/4.
 */
public abstract class TemplateActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TemplateActivity.class.getSimpleName();

    protected CarToolbar mToolbar;
    protected RelativeLayout mLLRoot;
    protected SwipeRefreshLayout mSRLLayout;
    protected FrameLayout mFLContentContainer;
    protected ProgressBar mPBLoading;
    protected TextView mTVHint;

    protected boolean mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_template);

        // 子类实现
        receiveIntent(getIntent());
        initViews();
    }

    /**
     * 处理 Intent
     * @param intent
     */
    protected abstract void receiveIntent(Intent intent);

    /**
     * 初始化View
     */
    protected void initViews() {
        mLLRoot = (RelativeLayout) findViewById(R.id.root);
        mToolbar = (CarToolbar) findViewById(R.id.toolbar);
        mSRLLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSRLLayout.setOnRefreshListener(this);
        mFLContentContainer = (FrameLayout) findViewById(R.id.content);

        initContentView(mFLContentContainer);
        mTVHint = (TextView) findViewById(R.id.empty);
        mPBLoading = (ProgressBar) findViewById(R.id.progress_bar);

        // 初始状态。
        mTVHint.setVisibility(View.GONE);
        mTVHint.setOnClickListener(this);
        mPBLoading.setVisibility(View.GONE);
        mLoading = true;

        // 请求数据
//        sendRequest(true);
    }

    /**
     * 初始化内容视图。由子类实现。
     * @param container
     */
    protected abstract void initContentView(FrameLayout container);

    /**
     *  由子类实现。
     * @param isRefresh 是否为刷新请求
     */
    protected abstract void sendRequest(boolean isRefresh);

    /**
     * 请求成功回调处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected abstract void onRequestSuccess(int requestCode, int resultCode, Bundle data);

    /**
     * 请求失败回调处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected abstract void onRequestFailed(int requestCode, int resultCode, Bundle data);

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * singleTask
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        super.handleBackgroundMessage(msg);
    }

    @Override
    protected void handleUIMessage(Message msg) {
        super.handleUIMessage(msg);
    }


    @Override
    public void onClick(View v) {
        if(v == mTVHint) {
//            mLoading = true;
            sendRequest(true);
        }
    }

    @Override
    public void onRefresh() {
        if(!mLoading) {
            mLoading = true;
            sendRequest(true);
        }
    }
}
