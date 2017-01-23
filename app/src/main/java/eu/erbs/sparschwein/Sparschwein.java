package eu.erbs.sparschwein;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class Sparschwein extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAcceleratorSensor;
    private TriggerEventListener mTriggerEventListener;

    private MediaPlayer mediaPlayerKaching;
    private MediaPlayer mediaPlayerShakeMany;

    public static String TAG = "SPARSCHWEIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparschwein);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcceleratorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mAcceleratorSensor , SensorManager.SENSOR_DELAY_NORMAL);

        //Amount text view
        TextView amountTextView = (TextView) findViewById(R.id.text_amount);

        //Buttons
        ((Button) findViewById(R.id.button_1cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.01f));
        ((Button) findViewById(R.id.button_2cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.02f));
        ((Button) findViewById(R.id.button_5cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.05f));
        ((Button) findViewById(R.id.button_10cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.10f));
        ((Button) findViewById(R.id.button_20cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.20f));
        ((Button) findViewById(R.id.button_50cent)).setOnClickListener(new InsertCoinListener(this, amountTextView, 0.50f));
        ((Button) findViewById(R.id.button_1euro)).setOnClickListener(new InsertCoinListener(this, amountTextView, 1f));
        ((Button) findViewById(R.id.button_2euro)).setOnClickListener(new InsertCoinListener(this, amountTextView, 2f));

        ((Button) findViewById(R.id.button_empty)).setOnClickListener(new EmptySafeListener(this, amountTextView));

        mediaPlayerKaching = MediaPlayer.create(this, R.raw.kaching);
        mediaPlayerShakeMany = MediaPlayer.create(this, R.raw.shakemany);

    }

    public void updateAmount() {
        Log.d(TAG, "Updated amount");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if(isShaken(event)){
            Log.d(TAG, "Shake it baby");
            mediaPlayerShakeMany.start();

        }

    }

    private boolean isShaken(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        final float alpha = 0.8f;

        float[] gravity = new float[]{0f,0f,0f};

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        float[] linear_acceleration = new float[3];
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        float totalAcceleration = Math.abs(linear_acceleration[0]) + Math.abs(linear_acceleration[1]) + Math.abs(linear_acceleration[2]);
        //Log.d(TAG, "Sensor changed: " + totalAcceleration + " " + linear_acceleration[0] + " " + linear_acceleration[1] + " " + linear_acceleration[2]);

        return totalAcceleration > 10;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "Sensor accuracy changed");
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAcceleratorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
