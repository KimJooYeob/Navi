package com.skplanetx.tmapopenmapapi.ui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import com.skplanetx.tmapopenmapapi.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

class MyCameraSurface extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	
	public MyCameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera = Camera.open();
		Camera.Parameters parameters = mCamera.getParameters();
		 parameters.setPictureSize(1280, 720);
		 mCamera.setParameters(parameters);
		try {
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}

	} 

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters params = mCamera.getParameters();
		List<Size> arSize = params.getSupportedPreviewSizes();

		if (arSize == null) {
			params.setPreviewSize(width, height);
		} else {
			int diff = 10000;
			Size opti = null;
			for (Size s : arSize) {
				if (Math.abs(s.height - height) < diff) {
					diff = Math.abs(s.height - height);
					opti = s;
				}
			}

			params.setPreviewSize(opti.width, opti.height);
		}
		mCamera.setParameters(params);
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

}

public class CameraActivity extends Activity {
	MyCameraSurface mSurface;
	Button mShutter;
	GPSTracker GPS;

	double latitude;
	double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);
		GPS = new GPSTracker(CameraActivity.this);
		latitude = GPS.getLatitude();
		longitude = GPS.getLongitude();
		if (GPS.canGetLocation()) // 위치 출력
			Toast.makeText(
					getApplicationContext(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
		else
			// 위치 찾을 수 없으면 에러~
			Toast.makeText(getApplicationContext(), "GPS ERROR",
					Toast.LENGTH_LONG).show();

		try {
			mSurface = (MyCameraSurface) findViewById(R.id.preview2);
		} catch (Exception e) {
			Toast.makeText(CameraActivity.this,
					"findByView Error : " + e.getMessage(), 10).show();
		}

		/*
		 * findViewById(R.id.focus).setOnClickListener(new
		 * Button.OnClickListener(){
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub mShutter.setEnabled(false);
		 * mSurface.mCamera.autoFocus(mAutoFocus);
		 * 
		 * } });
		 */
		mSurface.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("On Click!!");
				mSurface.mCamera.takePicture(null, null, mPicture);
				System.out.println("Take Picture!!");
			
				//SendFile();
				
				/*String pos = String.format("geo:%f,%f?z=16", latitude, longitude);
				Uri uri = Uri.parse(pos);
				*/
			
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				// TODO Auto-generated method stub
				return false;
			}
		});
		/*
		mShutter = (Button) findViewById(R.id.preview);
		mShutter.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*System.out.println("On Click!!");
				mSurface.mCamera.takePicture(null, null, mPicture);
				System.out.println("Take Picture!!");
				SendFile();
				System.out.println("SendFile!!");
				/*String pos = String.format("geo:%f,%f?z=16", latitude, longitude);
				Uri uri = Uri.parse(pos);
			
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}

		});*/
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		
	}

	private void SendFile(byte[] data) {
		
		System.out.println("On SendFile");
		String serverIp = "192.168.43.90";//IP주소
		System.out.println("IP : " + serverIp);
		FileSender fs = new FileSender(serverIp, latitude, longitude,data);
		fs.start();
		
		while (!fs.getState().equals(Thread.State.TERMINATED))
			;
		System.out.println("FileSender Thread has been ended");
	}

	AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
		// @Override
		public void onAutoFocus(boolean success, Camera camera) {
			mShutter.setEnabled(success);
		}
	};

	PictureCallback mPicture = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			// new SaveImageTask().execute(data);
			FileOutputStream outStream = null;
			//long CurrentTime = System.currentTimeMillis();
			// Write to SD Card
			System.out.println("onPictureTaken");
			try {
				
				//File sdCard = Environment.getExternalStorageDirectory();
				//File dir = new File(sdCard.getAbsolutePath() + "/Graduate/");
				//dir.mkdirs();
				
				//String fileName = String.format("1.jpg");
				//File outFile = new File(dir, fileName);
				//System.out.println("Delete before : "+outFile.exists());
				//outFile.delete();
				//System.out.println("Delete After : "+outFile.exists());
				SendFile(data);
				System.out.println("SendFile!!");
				/*outFile = new File(dir, fileName);
				outStream = new FileOutputStream(outFile);
				outStream.write(data);
				outStream.flush();
				outStream.close();
				*/
				// Log.d(C25_Camera.this, "onPictureTaken - wrote bytes: " +
				// data.length + " to " + outFile.getAbsolutePath());

				//refreshGallery(outFile);

			}catch(Exception e)
			{
				System.out.println("onTaken Picture Error :  " + e.getMessage());
			}

			// TCPClient Sender = new
			// TCPClient(String.valueOf(CurrentTime),data);
			// Sender.run();
			mSurface.mCamera.startPreview();
			// Log.d(C25_Camera.this, "onPictureTaken - jpeg");
		}
	};

	private void refreshGallery(File file) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(Uri.fromFile(file));
		sendBroadcast(mediaScanIntent);
	}




}
