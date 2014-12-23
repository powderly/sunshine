package org.wikipowdia.sunshine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Implements sensor-based scrolling of a ListView
 */
public class SensorListController implements SensorEventListener{

    static final String TAG = "SensorListController";

    Context mContext;

    ListView mList;

    SensorManager mSensorManager;

    private float[] mRotationMatrix = new float[16];

    private float[] mOrientation = new float[9];

    private float[] history = new float[2];

    private float mHeading;

    private float mPitch;

    boolean mActive = true;

    public SensorListController(Context context, ListView list) {
        this.mContext = context;
        this.mList = list;
        history[0] = 10;
        history[1] = 10;
    }


    /**
     * Should be called from the onResume() of Activity
     */
    public void onResume() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Should be called from the onPause() of Activity
     */
    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    /**
     * Toggles whether the controller modifies the view
     */
    public void toggleActive() {
        mActive = !mActive;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mList == null || !mActive) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
            SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, mRotationMatrix);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);

            mHeading = (float) Math.toDegrees(mOrientation[0]);
            mPitch = (float) Math.toDegrees(mOrientation[1]);

            float xDelta = history[0] - mHeading;  // Currently unused
            float yDelta = history[1] - mPitch;

            history[0] = mHeading;
            history[1] = mPitch;

            float Y_DELTA_THRESHOLD = 0.13f;

//            Log.d(TAG, "Y Delta = " + yDelta);

            int scrollHeight = mList.getHeight()
                    / 19; // 4 items per page, scroll almost 1/5 an item

//            Log.d(TAG, "ScrollHeight = " + scrollHeight);

            if (yDelta > Y_DELTA_THRESHOLD) {
//                Log.d(TAG, "Detected change in pitch up...");
                mList.smoothScrollBy(-scrollHeight, 0);
            } else if (yDelta < -Y_DELTA_THRESHOLD) {
//                Log.d(TAG, "Detected change in pitch down...");
                mList.smoothScrollBy(scrollHeight, 0);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void scrollToTop() {
        mList.smoothScrollToPosition(0);
    }
}