package com.example.oekaki_qiita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawingView extends View {
    public Paint paint;
    private Path path;
    private Canvas canvas;
    private Bitmap bitmap;

    public boolean eraserMode = false;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
    }
    //invalidate()呼ばれる
    @Override
    protected void onDraw(Canvas canvas) {
        //オフスクリーンキャンバスの内容を描画,画像みたいに張る
        canvas.drawBitmap(bitmap,0,0,null);
        //ペンモードの時、可視キャンバスに描画
        if (!eraserMode) {
            canvas.drawPath(path, paint);
        }
    }

    //画面が最初に生成されたときに呼ばれる
    @Override
    public void onSizeChanged( int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            //画面に指が触れたとき
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(x, y);
                invalidate();
                break;
            //指が触れている間
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            //画面から指が離れたとき
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                //現在のPathをオフスクリーンキャンバスと可視キャンパスに描画
                canvas.drawPath(path, paint);
                path.reset();
                invalidate();
                break;
        }
        return true;
    }
    //キャンバスを白紙に戻すメソッド
    public void delete(int w,int h) {
        //bitmapを初期化
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //初期化したbitmapをもとにキャンバスを初期化
        canvas = new Canvas(bitmap);
        //初期化したキャンバスを描画
        invalidate();
    }

    //色を変えるメソッド
    public void ColorChange(String color) {

        if (!eraserMode) {
            //引数のcolorを何も指定しないと(defaultにすると)デフォルト値の黒色になる
            paint.setXfermode(null);
            paint.setColor(Color.BLACK);
            switch (color) {
                case "red":
                    paint.setColor(Color.RED);
                    break;
                case "blue":
                    paint.setColor(Color.BLUE);
                    break;
                case "yellow":
                    paint.setColor(Color.YELLOW);
                    break;
                case "black":
                    paint.setColor(Color.BLACK);
                    break;
            }
        }
        else if (eraserMode) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
    }

    //ペンの太さを変えるメソッド
    public void PaintWidth(String width) {

        switch (width) {
            case "max":
                paint.setStrokeWidth(50);
                break;
            case "midium":
                paint.setStrokeWidth(30);
                break;
            case "min":
                paint.setStrokeWidth(10);
                break;
        }
    }
}