package com.thl.stocktaking.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.thl.stocktaking.R;
import com.thl.stocktaking.app.AppConst;
import com.thl.stocktaking.app.MyApp;
import com.thl.stocktaking.ui.activity.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionGen;

import static com.thl.stocktaking.utils.UIUtils.showToast;

public class SplashActivity extends BaseActivity {
    private String imei;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private Runnable startLoginPage = new Runnable() {
        @Override
        public void run() {
//            Intent intent;
//            intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long begin = System.currentTimeMillis();
        setContentView(R.layout.activity_splash);
        // 检查是否禁用JPush推送
        // checkIsReceiveMsgState();
        PermissionGen.with(this)
                .addRequestCode(ACTION_REQUEST_PERMISSIONS)
                .permissions(
                        //电话通讯录
//                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
//                        //位置
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.CAMERA,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_SETTINGS

//                        Manifest.permission.SYSTEM_ALERT_WINDOW

                )
                .request();
//        Handler handler = new Handler();
//        long end = System.currentTimeMillis();
//        long passedMillis = end - begin;
//        long delayedMillis = 3000 - passedMillis;
//        delayedMillis = delayedMillis < 0 ? 0 : delayedMillis;
//        handler.postDelayed(startLoginPage, delayedMillis);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        for (int i = 0; i < grantResults.length; i++) {
//
//            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                onPermissionFail();
//                return;
//            }
//        }

        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine(null);
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
        activeEngine(null);
        onPermissionSuccess();

    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context){
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public void onPermissionSuccess() {
//            final ProgressDialog mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.setTitle("loading register data...");
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyApp app = (MyApp) SplashActivity.this.getApplicationContext();
//                    app.mFaceDB.loadFaces();
                    SplashActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mProgressDialog.cancel();
                            Intent intent = new Intent(SplashActivity.this, RegisterAndRecognizeActivity.class);
//                            Intent intent = new Intent(SplashActivity.this, QRCodeScanActivity.class);
                            startActivityForResult(intent, 100);
                            finish();
                        }
                    });
                }
            },2000);
    }

    public void onPermissionFail() {
        Toast.makeText(this, "应用未获取到相应权限，无法使用，请前往设置界面打开应用所需权限!", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SplashActivity.this.finish();
                goToSetting(SplashActivity.this);
            }
        }, 2000);
    }

    /**
     * 激活引擎
     *
     * @param view
     */
    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(SplashActivity.this, AppConst.FaceAPP_ID, AppConst.FaceSDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

                        if (view != null) {
                            view.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

}
