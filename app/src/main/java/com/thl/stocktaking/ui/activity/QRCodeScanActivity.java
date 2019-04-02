package com.thl.stocktaking.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.thl.stocktaking.R;
import com.thl.stocktaking.utils.camera.RGBLuminanceSource;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

//import com.hzwy.apps.hotelapp.camera.QRCodeCamera.FindQRCodeThread;

public class QRCodeScanActivity extends CaptureActivity {
	private final String TAG = "QRCodeScanActivity";
	ProgressDialog m_pDialog = null;
	Bitmap mBitmap = null;
	

	private ProgressDialog dialog;
	private boolean flag;
	public static boolean Scan_success = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcodescan);
		init();
	}

	
	void init() {
		CAMERA_FACING = 0;
		m_pDialog = new ProgressDialog(this);
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
//		img = (ImageView)findViewById(R.id.photo);
//		img.setImageBitmap((Bitmap)getIntent().getExtras().get("bitmap"));



		
		surfaceView = (SurfaceView) findViewById(R.id.cameraWidgetId); // 预览
		
//		TextView rtnView =(TextView)findViewById(R.id.rtnView);
//		rtnView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Scan_success = false;
//				setResult(RESULT_CANCELED);
//				QRCodeScanActivity.this.finish();
//			}
//		});
//		if (CommonUtils.existOutSideVideo()){
//			camera = new QRCodeCamera(this, null, handler);
//			LinearLayout linearLayout = (LinearLayout)findViewById(R.id.cameraWidgetId);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//			lp.gravity = Gravity.CENTER;
//			lp.width = 640;
//			lp.height = 440;
//			linearLayout.addView(camera, lp);
//		}else{
//			handler.sendEmptyMessage(-4);
//		}
		
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(handler == null){
					return;
				}
				if (msg.what == -2){
					dialog.dismiss();
					String info = msg.getData().getString("info");
					dialogshow(info);
					
					Log.i(TAG, "扫描二维码成功!");
					
				}else if (msg.what == -1){
					dialog.dismiss();
					String info = msg.getData().getString("info");
					
					dialogshow(info);
					Log.i(TAG, "扫描二维码失败 what:-1");
				}else if (msg.what == 0){
					dialog.dismiss();
//					mBitmap = msg.getData().getParcelable("bitmap");
//					flag = msg.getData().getBoolean("flag");//是否是网证接口获取到的数据
//					img.setImageBitmap(mBitmap);

					//设置身份证信息
//					nameTxt.setText(guestBean.getName());
//					sexTxt.setText(guestBean.getSex_cn());
//					nationalityTxt.setText(guestBean.getNationality_cn());
//					birthTxt.setText(guestBean.getBirth());
//					addressTxt.setText(guestBean.getAddress());
//					cardNoTxt.setText(guestBean.getCertno());


//					Log.i(TAG, "扫描二维码成功 what:0");
				}
				else if (msg.what == -4){
					if(dialog!=null){
						dialog.dismiss();
					}
					Log.e(TAG, "外置摄像头错误! what:-4");
					doFinishDelay();
					AlertDialog.Builder customBuilder = new
							AlertDialog.Builder(QRCodeScanActivity.this);
			            customBuilder.setTitle("错误")
			                .setMessage("外置摄像头错误，请重新插拔摄像头!")
			                .setPositiveButton("确定", 
			                        new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int which) {
			                    	QRCodeScanActivity.this.finish();
			                    	dialog.dismiss();
			                    }
			                });
			            AlertDialog dialog = customBuilder.create();
			            dialog.show();
				}else if (msg.what == 1){//更新
					//二维码扫描成功正在验证身份信息
					
//					dialog = new ProgressDialog(QRCodeScanActivity.this);
//					dialog.setMessage("二维码扫描成功，正在获取身份信息请稍候");
//					dialog.setCancelable(false);
//					dialog.show();
					Toast.makeText(QRCodeScanActivity.this, "货品不存在",Toast.LENGTH_SHORT).show();
					Scan_success = false;
				}
			}
		};
	}
	
	public void doFinishDelay(){
					
		if(null != handler) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
//		try {
//			Thread.sleep(500l);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
//			QRCodeScanActivity.this.setResult(BaseCamera.RESULT_CODE_FACE_DECT_FAIL);
			QRCodeScanActivity.this.finish();
         }
         return super.onKeyDown(keyCode, event);
     }
	
	private void dialogshow(String info){
		AlertDialog.Builder customBuilder = new
				AlertDialog.Builder(QRCodeScanActivity.this);
		customBuilder.setTitle("提示")
        .setMessage(""+info)
        .setCancelable(false);
		customBuilder.setPositiveButton("重新扫描", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	Scan_success = false;
                    	dialog.dismiss();
                    }
                });
//		customBuilder.setNegativeButton("取消办理", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            	doFinishDelay();
//            	QRCodeScanActivity.this.setResult(BaseCamera.RESULT_CODE_FACE_DECT_FAIL);
//            	QRCodeScanActivity.this.finish();
//            	dialog.dismiss();
//            }
//        });
		customBuilder.show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(null != handler) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
		Scan_success = false;
		mBitmap = null;
	}



	class FindQRCodeThread extends Thread {
		byte[] orgiPreviewBuf = null;
        Camera.Size size;
        public FindQRCodeThread(byte[] bytes,Camera.Size size) {
            this.orgiPreviewBuf = bytes;
            this.size = size;
        }

		public void run() {
			YuvImage yuvImage = null;
            ByteArrayOutputStream os = null;
            byte[] _bytes = null;
            Bitmap captureBmp =  null;
			try {
				yuvImage = new YuvImage(orgiPreviewBuf, ImageFormat.NV21, size.width, size.height, null);
                os = new ByteArrayOutputStream(orgiPreviewBuf.length);
                if(!yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, os)){
                    return;
                }

                _bytes = os.toByteArray();

                captureBmp = BitmapFactory.decodeByteArray(_bytes, 0,
                        _bytes.length);
				Result result = parseQRcodeBitmap(captureBmp);
				
				if (result == null) {
					Log.d("CameraWidget", "FindFaceAndIdentThread 未能识别出二维码!");
					return;
				} else {
					//扫码成功正在获取二维码信息
					if(result.getText()!=null&&result.getText()!=""){
						MediaPlayer mMediaPlayer;
						mMediaPlayer=MediaPlayer.create(QRCodeScanActivity.this, R.raw.beep);
						mMediaPlayer.start();
						if(!Scan_success){
							Scan_success = true;
							sendNotify(1, "二维码扫描成功，正在获取身份信息请稍候！", -1f);
						}
						sleep(2000);
//						sendNotify(0, "识别出二维码！", -1f);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
//				isAnalyzing = false;
//				CommonUtils.recycleBitmap(captureBmp);
				captureBmp = null;
				threadNum--;
			}
		}
	}
	
	   private com.google.zxing.Result  parseQRcodeBitmap(Bitmap bitmap){
	        //解析转换类型UTF-8  
		   Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
	        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
	        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象  
	        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
	        //将图片转换成二进制图片  
	        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
	        //初始化解析对象  
	        QRCodeReader reader = new QRCodeReader();
	        //开始解析  
	        Result result = null;
	        try {  
	            result = reader.decode(binaryBitmap, hints);  
	        } catch (Exception e) {  
	            e.printStackTrace();
	        }
			return result;
	    }


	@Override
	public void onPreviewFrame(byte[] bytes, Camera camera) {
		// TODO Auto-generated method stub
		Camera.Size size = camera.getParameters().getPreviewSize();
//		if (!isAnalyzing){        		
    		if (threadNum < 1){
   	        	 Log.d("CameraWidget","thread create");
//   	        	isAnalyzing = true;
   	            FindQRCodeThread thread = new FindQRCodeThread(bytes,size);
   	            threadNum++;
   	            thread.start();
    		}
//    	}
		
		
		
	}
}
