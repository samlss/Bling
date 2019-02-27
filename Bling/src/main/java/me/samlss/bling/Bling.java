package me.samlss.bling;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.view.ViewGroup;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description 随机形状下落效果
 */
public class Bling {
    private BlingView mBlingView;

    private Bling(BlingView blingView){
        //internal method
        mBlingView = blingView;
    }

    public boolean isShowing(){
        return mBlingView.isShowing();
    }

    /**
     * Start the shapes falling animation.
     *
     * @param shapeType Must be one of {@link BlingType}
     * */
    public void show(int shapeType) {
        mBlingView.show(shapeType);
    }

    /**
     * Dismiss the shapes, event though they are doing animation.
     * */
    public void dismiss() {
        mBlingView.dismiss();
    }

    /**
     * To release Bling & remove the bling view immediately.
     * */
    public void release() {
        mBlingView.release();
    }

    /**
     * In order to build an object of {@link Bling}
     * */
    public static class Builder{
        private Bling mBling;
        protected BlingView mBlingView;

        /**
         * The shapes will display in the {@link android.R.id#content} of the activity
         *
         * @param activity The activity that the shapes will be displaying.
         * */
        public Builder(Activity activity){
            this((ViewGroup) activity.findViewById(android.R.id.content));
        }

        /**
         * The shapes will display in the viewgroup.
         *
         * @param viewGroup The viewGroup that the shapes will be displaying.
         * */
        public Builder(ViewGroup viewGroup){
            this.mBlingView = new BlingView(viewGroup.getContext());
            viewGroup.addView(mBlingView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            mBling = new Bling(this.mBlingView);
        }

        /**
         * Set the drop time of the shapes.
         *
         * @param duration The drop time, in milliseconds.
         * */
        public Builder setDuration(int duration){
            mBlingView.setDuration(duration);
            return this;
        }

        /**
         * Set the drop interpolator of the shapes.
         *
         * @param interpolator The drop interpolator,
         *                     the default is {@link android.view.animation.LinearInterpolator}.
         * */
        public Builder setInterpolator(TimeInterpolator interpolator){
            mBlingView.setInterpolator(interpolator);
            return this;
        }

        /**
         * Set whether to hide the shapes when the animation ends
         *
         * @param autoHide True the shapes will be hided, otherwise not.
         * */
        public Builder setAutoHide(boolean autoHide){
            mBlingView.setAutoHide(autoHide);
            return this;
        }

        /**
         * Set how many shapes will fall.
         *
         * @param count The count of the Shapes.
         * */
        public Builder setShapeCount(int count){
            mBlingView.setShapeCount(count);
            return this;
        }

        /**
         * Set the range of the shape radius.
         *
         * @param minRadius The minimum radius of shapes.
         * @param maxRadius The maximum radius of shapes.
         * */
        public Builder setRadiusRange(float minRadius, float maxRadius){
            mBlingView.setRadiusRange(minRadius, maxRadius);
            return this;
        }

        /**
         * Set shape drop speed.
         *
         * @param minSpeed The minimum speed of shapes.
         * @param maxSpeed The maximum speed of shapes.
         * */
        public Builder setSpeedRange(float minSpeed, float maxSpeed){
            mBlingView.setSpeedRange(minSpeed, maxSpeed);
            return this;
        }

        /**
         * Set the rotation range of falling shapes.
         * If the shapes are {@link BlingType#CIRCLE}, this parameter not works.
         *
         * @param minRotation The minimum rotation of shapes.
         * @param maxRotation The maximum rotation of shapes.
         * */
        public Builder setRotationRange(float minRotation, float maxRotation){
            mBlingView.setRotationRange(minRotation, maxRotation);
            return this;
        }

        /**
         * Set the rotation speed range of falling shapes.
         * If the shapes are {@link BlingType#CIRCLE}, this parameter not works.
         *
         * @param minRotationSpeed The minimum rotation speed of shapes.
         * @param maxRotationSpeed The maximum rotation speed of shapes.
         * */
        public Builder setRotationSpeedRange(float minRotationSpeed, float maxRotationSpeed){
            mBlingView.setRotationSpeedRange(minRotationSpeed, maxRotationSpeed);
            return this;
        }

        /**
         * Set the bling listener.
         *
         * @param blingListener The bling listener
         * */
        public Builder setBlingListener(BlingListener blingListener){
            mBlingView.setBlingListener(blingListener);
            return this;
        }

        /**
         * Set the colors of shapes.
         *
         * @param colors The colors array.
         * */
        public Builder setColors(int ... colors){
            mBlingView.setColors(colors);
            return this;
        }

        public Bling build(){
            return mBling;
        }
    }
}
