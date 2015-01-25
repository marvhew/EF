package com.example.efproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class EditPhoto extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_photo);
		Bitmap bitmap = (Bitmap)getIntent().getExtras().get("Photo");
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		DrawingView drawingView = (DrawingView) findViewById(R.id.Drawing1);
		drawingView.getLayoutParams().width = width;
		drawingView.getLayoutParams().height = height; 
		drawingView.setImageBitmap(bitmap);
		Spinner spinner = (Spinner)findViewById(R.id.Spn_Colors);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_photo, menu);
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
	
	public void Click_Cancel(View v)
	{
		setResult(RESULT_CANCELED);
		finish();
	}
	public void Click_OK(View v)
	{
		setResult(RESULT_OK);
		finish();
	}
	public void Click_Apply(View v)
	{
		float stroke = 0;
		EditText edit = (EditText)findViewById(R.id.Edt_Stroke);
		if(!edit.getText().toString().isEmpty())
			stroke = Float.parseFloat(edit.getText().toString());
		
		DrawingView drawingView = (DrawingView) findViewById(R.id.Drawing1);
		drawingView.SetStrokeWidth(stroke);
	}
}
