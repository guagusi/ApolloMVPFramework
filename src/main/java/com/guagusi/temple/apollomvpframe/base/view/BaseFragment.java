package com.guagusi.temple.apollomvpframe.base.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.guagusi.temple.apollomvpframe.base.widget.CustomAlertDialog;
import com.guagusi.temple.apollomvpframe.base.widget.HintDialog;

import java.lang.ref.WeakReference;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    // 带消息队列的工作线程
    private HandlerThread mHandlerThread;

    protected BackgroundHandler mBackgroundHandler;

    protected Handler mUIHandler;

    protected int mResId = -1;
    protected ViewGroup mViewRoot;

    protected HintDialog mHintDialog;

    protected CustomAlertDialog mAlertDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();

        mBackgroundHandler = new BackgroundHandler(this, mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(getCurrentActivity() != null) {
                    getCurrentActivity().handleBackgroundMessage(msg);
                }
            }
        };

        mUIHandler = new UIHandler(this) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(getCurrentActivity() != null) {
                    getCurrentActivity().handleUIMessage(msg);
                }
            }
        };

    }

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setFragmentLayout();
        if(mResId != -1) {
            View rootView = inflater.inflate(mResId, container, false);
            return rootView;
        }

        throw new NullPointerException("Fragment 的布局为空");
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewRoot = (ViewGroup) view;
        initViews(mViewRoot);
        initViews(mViewRoot, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 具体实现类覆写。处理消息
     * @param msg
     */
    protected void handleUIMessage(Message msg) {

    }

    public void sendUIMessage(Message msg) {
        mUIHandler.sendMessage(msg);
    }

    public void sendUIMessageDelayed(Message msg, long delayedMills) {
        mUIHandler.sendMessageDelayed(msg, delayedMills);
    }

    public void sendEmptyUIMessage(int what) {
        mUIHandler.sendEmptyMessage(what);
    }

    public void sendEmptyUIMessageDelayed(int what, long delayedMills) {
        mUIHandler.sendEmptyMessageDelayed(what, delayedMills);
    }

    /**
     * 具体实现类覆写。处理消息
     * @param msg
     */
    protected void handleBackgroundMessage(Message msg) {

    }

    public void sendBackgroundMessage(Message msg) {
        mBackgroundHandler.sendMessage(msg);
    }

    public void sendBackgroundMessageDelayed(Message msg, long delayMills) {
        mBackgroundHandler.sendMessageDelayed(msg, delayMills);
    }

    public void sendEmptyBackgroundMessage(int what) {
        mBackgroundHandler.sendEmptyMessage(what);
    }

    public void sendEmptyBackgroundMessageDelayed(int what, long delayMills) {
        mBackgroundHandler.sendEmptyMessageDelayed(what, delayMills);
    }

    /**
     *
     * @param msg
     */
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * toast
     * @param resId
     */
    public  void showToast(int resId) {
        showToast(getResources().getString(resId));
    }
    /**
     * 用户点击返回，touch无法取消
     * @param type
     * @param msg
     */
    public void showHintDialog(int type, String msg) {
        if(mHintDialog == null) {
            mHintDialog = new HintDialog(getActivity());
            mHintDialog.setCanceledOnTouchOutside(false);
            mHintDialog.setCancelable(false);
        }
        mHintDialog.showHintDialog(type, msg);
    }

    /**
     * 用户点击返回，touch无法取消
     */
    public void dismissHintDialog() {
        if(mHintDialog != null) {
            if(mHintDialog.isShowing()) {
                mHintDialog.dismiss();
            }
        }
    }

    /**
     * 用户点击返回，touch无法取消
     * @param msg
     */
    public void showOneConfirmAlertDialog(String title, String msg, CustomAlertDialog.ActionCallback callback) {
        if(mAlertDialog == null) {
            mAlertDialog = new CustomAlertDialog(getActivity());
        }
        mAlertDialog.dismissAlertDialog();
        mAlertDialog.setUp(CustomAlertDialog.TYPE_ONE_BUTTON, callback);
        mAlertDialog.setActionCallback(callback);
        mAlertDialog.showAlertDialog(CustomAlertDialog.TYPE_ONE_BUTTON, title, msg);
    }

    /**
     * 用户点击返回，touch无法取消
     */
    public void dismissAlertDialog() {
        if(mAlertDialog != null) {
            mAlertDialog.dismissAlertDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        dismissAlertDialog();
        dismissHintDialog();
    }

    /**
     * 初始化
     * @param viewRoot
     */
    protected abstract void initViews(ViewGroup viewRoot);

    /**
     * 初始化
     * @param viewRoot
     */
    protected void initViews(ViewGroup viewRoot, Bundle savedInstanceState) {}

    /**
     * 设置Fragment的布局。mResId = resId
     * @return
     */
    protected abstract int setFragmentLayout();


    /**
     * 后台消息队列处理器
     */
    private static class BackgroundHandler extends Handler {

        private WeakReference<BaseFragment> mActivityRef;

        public BackgroundHandler(BaseFragment baseWorkerFragmentActivity, Looper looper) {
            super(looper);

            mActivityRef = new WeakReference<BaseFragment>
                    (baseWorkerFragmentActivity);
        }

        public BaseFragment getCurrentActivity() {
            return mActivityRef.get();
        }
    }

    /**
     * 主线程消息队列处理器
     */
    private static class UIHandler extends Handler {

        private final WeakReference<BaseFragment> mActivityRef;

        public UIHandler(BaseFragment baseFragmentActivity) {
            mActivityRef = new WeakReference<BaseFragment>(baseFragmentActivity);
        }

        public BaseFragment getCurrentActivity() {
            return mActivityRef.get();
        }
    }
}
