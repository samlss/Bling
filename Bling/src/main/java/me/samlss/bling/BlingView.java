package me.samlss.bling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description bling bling bling view...
 */
class BlingView extends View {
    private int mDuration = 2500;
    private TimeInterpolator mInterpolator = new LinearInterpolator();
    private int mShapeCount = 20;
    private float mMinRadius = 5;
    private float mMaxRadius = 10;
    private float mMinSpeed = 0.08f;
    private float mMaxSpeed = 0.3f;
    private float mMinRotation = 0;
    private float mMaxRotation = 360;
    private float mMinRotationSpeed = 0;
    private float mMaxRotationSpeed = 0;
    private BlingListener mBlingListener;
    private List<Shape> mShapes;
    private Random mRandom;
    private boolean isAutoHide;
    private boolean isShowing;
    private Random mMixedRandom;
    private int[] mColors = new int[]{
            Color.WHITE,
            Color.MAGENTA,
            Color.YELLOW,
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.CYAN
    };

    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private Matrix mMatrix;
    private Path mDrawPath;

    public BlingView(Context context) {
        this(context, null);
    }

    public BlingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mShapes = new ArrayList<>();
        mRandom = new Random();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mMatrix = new Matrix();
        mDrawPath = new Path();
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setShapeCount(int count) {
        mShapeCount = count;
    }

    public void setRadiusRange(float minRadius, float maxRadius) {
        mMinRadius = minRadius;
        mMaxRadius = maxRadius;
    }

    public void setSpeedRange(float minSpeed, float maxSpeed) {
        mMinSpeed = minSpeed;
        mMaxSpeed = maxSpeed;
    }

    public void setRotationRange(float minRotation, float maxRotation) {
        mMinRotation = minRotation;
        mMaxRotation = maxRotation;
    }

    public void setRotationSpeedRange(float minRotationSpeed, float maxRotationSpeed) {
        mMinRotationSpeed = minRotationSpeed;
        mMaxRotationSpeed = maxRotationSpeed;
    }

    public void setBlingListener(BlingListener blingListener) {
        mBlingListener = blingListener;
    }

    public void setColors(int... colors) {
        mColors = colors;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void show(int shapeType) {
        createShapes(shapeType);
        startAnimation();
    }

    public void setAutoHide(boolean autoHide) {
        isAutoHide = autoHide;
    }

    private void createShapes(int shapeType){
        cancelAnimator();
        mShapes.clear();

        for (int i = 0; i < mShapeCount; i++){
            Shape shape = new Shape();
            shape.isActivated = true;
            shape.radius = mRandom.nextFloat()*(mMaxRadius-mMinRadius) + mMinRadius;
            shape.x = shape.radius + mRandom.nextInt((int) (getMeasuredWidth() - 2 * shape.radius));
            shape.y = -getMeasuredHeight() / 3 + mRandom.nextInt(getMeasuredHeight());
            shape.speed = mRandom.nextFloat()*(mMaxSpeed-mMinSpeed) + mMinSpeed;
            shape.rotation = mRandom.nextFloat()*(mMaxRotation-mMinRotation) + mMinRotation;
            shape.rotationSpeed = mRandom.nextFloat()*(mMaxRotationSpeed-mMinRotationSpeed) + mMinRotationSpeed;
            shape.color = mColors[mRandom.nextInt(mColors.length)];
            shape.path = createShape(shape.radius, shape.x, shape.y, shapeType);
            mShapes.add(shape);
        }
    }

    private Path createShape(float radius, float x, float y, int shapeType){
        switch (shapeType){
            case BlingType.STAR:
                return createStarPath(radius, x, y);

            case BlingType.RECTANGLE:
                return createRectPath(radius, x, y);

            case BlingType.TRIANGLE:
                return createTriangle(radius, x, y);

            case BlingType.MIXED:
                return createMixedPath(radius, x, y);
        }

        return createCirclePath(radius, x, y);
    }

    private Path createMixedPath(float radius, float x, float y){
        if (mMixedRandom == null){
            mMixedRandom = new Random();
        }

        switch (mMixedRandom.nextInt(4)){
            case 0:
                return createCirclePath(radius, x, y);
            case 1:
                return createRectPath(radius, x, y);
            case 2:
                return createTriangle(radius, x, y);
            case 3:
                return createStarPath(radius, x, y);
        }

        return createCirclePath(radius, x, y);
    }

    private Path createCirclePath(float radius, float x, float y){
        Path path = new Path();
        path.addCircle(x, y, radius, Path.Direction.CW);
        return path;
    }

    private Path createTriangle(float radius, float x, float y){
        radius *= 2.5f;
        Path path = new Path();
        path.moveTo(radius, 0);
        path.lineTo(-radius / 2f, -radius / 2f);
        path.lineTo(-radius / 2f, radius /2f);
        path.lineTo(radius, 0);

        Matrix matrix = new Matrix();
        matrix.postTranslate(x, y);
        path.transform(matrix);

        return path;
    }

    private Path createRectPath(float radius, float x, float y){
        Path path = new Path();
        path.addRect(x - radius * 2, y - radius,
                x + radius * 2, y + radius, Path.Direction.CW);

        return path;
    }

    private Path createStarPath(float radius, float x, float y){
        Path path = new Path();
        float R = radius * 2;
        float r = radius;

        List<PointF> starPoints = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            double outerDoc = (18 + 72 * i) / 180d * Math.PI;
            PointF pointF1 = new PointF((float) (Math.cos(outerDoc) * R) ,
                    - (float) (Math.sin(outerDoc) * R));

            double innerDoc = (54 + 72 * i) / 180d * Math.PI;
            PointF pointF2 = new PointF((float)(Math.cos(innerDoc) * r) ,
                    -(float) (Math.sin(innerDoc) * r));

            starPoints.add(pointF1);
            starPoints.add(pointF2);
        }

        path.moveTo(starPoints.get(0).x, starPoints.get(0).y);
        for (int i = 1; i < starPoints.size(); i++){
            path.lineTo(starPoints.get(i).x, starPoints.get(i).y);
        }
        path.lineTo(starPoints.get(0).x, starPoints.get(0).y);

        Matrix matrix = new Matrix();
        matrix.postTranslate(x, y);
        path.transform(matrix);

        return path;
    }

    private void startAnimation(){
        mValueAnimator = ValueAnimator.ofInt(0, mDuration);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
        mValueAnimator.addListener(mAnimatorListener);
        mValueAnimator.start();
    }

    private void cancelAnimator(){
        if (mValueAnimator != null){
            mValueAnimator.removeListener(mAnimatorListener);
            mValueAnimator.removeUpdateListener(mAnimatorUpdateListener);

            if (mValueAnimator.isRunning()) {
                mValueAnimator.cancel();
            }
            mValueAnimator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShapes.isEmpty()){
            return;
        }

        for (Shape shape : mShapes){
            if (shape.isActivated){
                mMatrix.reset();
                mDrawPath.reset();
                mMatrix.postRotate(shape.rotation, shape.x, shape.y);
                mMatrix.postTranslate(0, shape.translateY);
                shape.path.transform(mMatrix, mDrawPath);

                mPaint.setColor(shape.color);
                canvas.drawPath(mDrawPath, mPaint);
            }
        }
    }

    public void dismiss() {
        isShowing = false;
        cancelAnimator();
        if (isAutoHide) {
            setVisibility(GONE);
        }
    }

    public void release() {
        cancelAnimator();
        mShapes.clear();
        if (getParent() != null){
            try {
                ((ViewGroup) getParent()).removeView(this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void onAnimatorUpdate(int animatedValue) {
        for (Shape shape : mShapes){
            shape.translateY = shape.y + shape.speed * animatedValue;
            shape.rotation += shape.rotationSpeed;
            if (shape.y + shape.translateY - shape.radius > getMeasuredHeight()){
                shape.isActivated = false;
            }
        }
        invalidate();
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            onAnimatorUpdate((int) animation.getAnimatedValue());
        }
    };

    private AnimatorListenerAdapter mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            if (isAutoHide) {
                setVisibility(GONE);
            }

            isShowing = false;
            if (mBlingListener != null){
                mBlingListener.onEnd();
            }
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);

            setVisibility(VISIBLE);

            isShowing = true;
            if (mBlingListener != null){
                mBlingListener.onBegin();
            }
        }
    };

    private class Shape{
        boolean isActivated;
        float x;
        float y;
        float radius;
        float translateY;
        float speed;
        float rotation;
        float rotationSpeed;
        int color;
        Path path;
    }
}
