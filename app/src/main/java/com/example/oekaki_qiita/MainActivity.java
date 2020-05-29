package com.example.oekaki_qiita;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //View
        drawingView = findViewById(R.id.drawing_view);
        //カラーボタン
        findViewById(R.id.button_delete).setOnClickListener(deletePainting);
        findViewById(R.id.button_black).setOnClickListener(blackPainting);
        findViewById(R.id.button_red).setOnClickListener(redPainting);
        findViewById(R.id.button_blue).setOnClickListener(bluePainting);
        findViewById(R.id.button_yellow).setOnClickListener(yellowPainting);
        //ペンと消しゴム
        findViewById(R.id.button_pen).setOnClickListener(Painting);
        findViewById(R.id.button_eraser).setOnClickListener(Erasing);
        //ペンの太さボタン
        findViewById(R.id.button_pen_width_max).setOnClickListener(Maxing);
        findViewById(R.id.button_pen_width_midium).setOnClickListener(Midiuming);
        findViewById(R.id.button_pen_width_min).setOnClickListener(Mining);
    }

    //deleteボタンが押されたときの処理
    View.OnClickListener deletePainting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //ダイアログ
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("本当にすべて消しますか？");
            //Okボタンでdelete実行
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Viewの横と高さを取得
                    int w = drawingView.getWidth();
                    int h = drawingView.getHeight();
                    drawingView.delete(w,h);
                }
            });
            //CancelでDeleteを解除
            builder.setNegativeButton("NO", null)
                    .show();
        }
    };
    //blackボタンが押されたときの処理
    View.OnClickListener blackPainting = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            drawingView.ColorChange("black");
        }
    };
    //redボタンが押されたときの処理
    View.OnClickListener redPainting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.ColorChange("red");
        }
    };
    //blueボタンが押されたときの処理
    View.OnClickListener bluePainting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.ColorChange("blue");
        }
    };
    //yellowボタンが押されたときの処理
    View.OnClickListener yellowPainting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.ColorChange("yellow");
        }
    };
    //ペンモードボタン(黒色)
    View.OnClickListener Painting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.eraserMode = false;
            //ペンはデフォルトで小の太さ
            drawingView.paint.setStrokeWidth(10);
            //何かしらのカラーボタンを押さないとデフォルトの黒色にする
            drawingView.ColorChange("default");
        }
    };
    //消しゴムボタン
    View.OnClickListener Erasing = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.eraserMode = true;
            //消しゴムはデフォルトで中の太さ
            drawingView.paint.setStrokeWidth(30);
            drawingView.ColorChange("TRANSPARENT");
        }
    };
    View.OnClickListener Maxing = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.PaintWidth("max");
        }
    };
    View.OnClickListener Midiuming = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.PaintWidth("midium");
        }
    };
    View.OnClickListener Mining = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawingView.PaintWidth("min");
        }
    };
}
