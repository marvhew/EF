package com.example.efproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SlidingDrawer.OnDrawerCloseListener;

public class DrawingView extends ImageView {
	private Paint paint = new Paint();
	private Path path = new Path();

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}
	
	public void SetPaintColor(int color)
	{
		paint.setColor(color);
	}
	
	public void SetStrokeWidth(float width)
	{
		paint.setStrokeWidth(width);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
		canvas.save();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float eventX = event.getX();
		float eventY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(eventX, eventY);
			break;
		default:
			return false;
		}
		invalidate();
		return super.onTouchEvent(event);
	}
}
