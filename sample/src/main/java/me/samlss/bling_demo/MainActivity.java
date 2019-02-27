package me.samlss.bling_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import me.samlss.bling.Bling;
import me.samlss.bling.BlingListener;
import me.samlss.bling.BlingType;

public class MainActivity extends AppCompatActivity {
    private Bling mBling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] colors = new int[]{
                Color.parseColor("#F44336"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#009688"),

                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#5AB963"),
                Color.parseColor("#FFC107"),

                Color.parseColor("#9C28B0"),
                Color.parseColor("#03A9F4"),
                Color.parseColor("#8BC34A"),
                Color.parseColor("#FF9800"),

                Color.parseColor("#673AB7"),
                Color.parseColor("#00BCD4"),
                Color.parseColor("#CDDC39"),
                Color.parseColor("#FF5722"),
        };

        mBling = new Bling.Builder((ViewGroup) getWindow().getDecorView())
                .setDuration(8000)
                .setShapeCount(66)
                .setRadiusRange(10, 20)
                .setRotationSpeedRange(-3f, 3f)
                .setAutoHide(true)
                .setColors(colors)
                .setSpeedRange(0.1f, 0.5f)
                .setRotationRange(90, 150)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .build();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_circle:
                mBling.show(BlingType.CIRCLE);
                break;

            case R.id.btn_rectangle:
                mBling.show(BlingType.RECTANGLE);
                break;

            case R.id.btn_triangle:
                mBling.show(BlingType.TRIANGLE);
                break;

            case R.id.btn_star:
                mBling.show(BlingType.STAR);
                break;

            case R.id.btn_mixed:
                mBling.show(BlingType.MIXED);
                break;
        }
    }
}
