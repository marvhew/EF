package com.example.efproject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_IMAGE_EDIT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.Btn_EditPhoto).setEnabled(false);
		findViewById(R.id.Btn_SavePhoto).setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void ClickTakePhotoButton(View v) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
		}
	}

	public void ClickEditPhotoButton(View v) {
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		Intent intent = new Intent(this, EditPhoto.class);
		intent.putExtra("Photo",
				((BitmapDrawable) imageView.getDrawable()).getBitmap());
		startActivityForResult(intent, REQUEST_IMAGE_EDIT);
	}

	public void ClickSavePhotoButton(View v) {
		if (isExternalStorageReadable() && isExternalStorageWritable()) {
			ImageView view = (ImageView) findViewById(R.id.imageView1);
			SaveImage(((BitmapDrawable) view.getDrawable()).getBitmap());
		} else {
			Context context = getApplicationContext();
			CharSequence text = "Error!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	private boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	private void SaveImage(Bitmap finalBitmap) {
		String fname = RandomFileName();
		try {
			MediaStore.Images.Media.insertImage(getContentResolver(),
					finalBitmap, fname, "");
		} catch (Exception e) {
			Context context = getApplicationContext();
			CharSequence text = "Error!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	private String RandomFileName() {
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "EFImage-" + n + ".jpg";
		return fname;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.getLayoutParams().width = imageBitmap.getWidth();
			imageView.getLayoutParams().height = imageBitmap.getHeight();
			imageView.setImageBitmap(imageBitmap);
			findViewById(R.id.Btn_EditPhoto).setEnabled(true);
			findViewById(R.id.Btn_SavePhoto).setEnabled(true);
		} else if (requestCode == REQUEST_IMAGE_EDIT && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("drawing");
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.getLayoutParams().width = imageBitmap.getWidth();
			imageView.getLayoutParams().height = imageBitmap.getHeight();
			imageView.setImageBitmap(imageBitmap);
		}
	}
}
