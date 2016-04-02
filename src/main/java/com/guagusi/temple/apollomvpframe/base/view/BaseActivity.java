package com.guagusi.temple.apollomvpframe.base.view;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.guagusi.temple.apollomvpframe.base.BaseApplication;
import com.guagusi.temple.apollomvpframe.base.widget.CustomAlertDialog;
import com.guagusi.temple.apollomvpframe.base.widget.HintDialog;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by maozi on 2015/5/25.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    // 带消息队列的工作线程
    private HandlerThread mHandlerThread;

    protected BackgroundHandler mBackgroundHandler;

    protected Handler mUIHandler;

    protected ActivityManager mActivityManager;

    protected HintDialog mHintDialog;

    protected CustomAlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissHintDialog();
        dismissAlertDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBackgroundHandler != null && mBackgroundHandler.getLooper() != null) {
            mBackgroundHandler.getLooper().quit();
        }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
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
            mHintDialog = new HintDialog(this);
            mHintDialog.setCanceledOnTouchOutside(false);
            mHintDialog.setCancelable(false);
        }
        mHintDialog.dismiss();
        mHintDialog.showHintDialog(type, msg);
    }

    /**
     * 用户点击返回，touch无法取消
     */
    public void dismissHintDialog() {
        if(mHintDialog != null) {
            mHintDialog.dismiss();
        }
    }

    /**
     * 用户点击返回，touch无法取消
     * @param msg
     */
    public void showOneConfirmAlertDialog(String title, String msg, CustomAlertDialog.ActionCallback callback) {
        if(mAlertDialog == null) {
            mAlertDialog = new CustomAlertDialog(this);
        }
        if(mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        mAlertDialog.setUp(CustomAlertDialog.TYPE_ONE_BUTTON, callback);
        mAlertDialog.setActionCallback(callback);
        mAlertDialog.showAlertDialog(CustomAlertDialog.TYPE_ONE_BUTTON, title, msg);
    }

    /**
     * 用户点击返回，touch无法取消
     */
    public void dismissAlertDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismissAlertDialog();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }

    /**
     * 隐藏软键盘
     * @param context
     */
    public void hideSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 是否在ActivityStack的栈顶
     * @return
     */
    public boolean isAppOnForeground() {
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (tasksInfo.get(0).topActivity.getPackageName().
                    equals(BaseApplication.getInstance().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否锁屏
     * @return
     **/
    public boolean isScreenLock(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 后台消息队列处理器
     */
    private static class BackgroundHandler extends Handler {

        private WeakReference<BaseActivity> mActivityRef;

        public BackgroundHandler(BaseActivity baseWorkerFragmentActivity, Looper looper) {
            super(looper);

            mActivityRef = new WeakReference<BaseActivity>
                    (baseWorkerFragmentActivity);
        }

        public BaseActivity getCurrentActivity() {
            return mActivityRef.get();
        }
    }

    /**
     * 主线程消息队列处理器
     */
    private static class UIHandler extends Handler {

        private final WeakReference<BaseActivity> mActivityRef;

        public UIHandler(BaseActivity baseFragmentActivity) {
            mActivityRef = new WeakReference<BaseActivity>(baseFragmentActivity);
        }

        public BaseActivity getCurrentActivity() {
            return mActivityRef.get();
        }
    }
}
