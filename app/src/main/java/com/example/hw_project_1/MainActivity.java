package com.example.hw_project_1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xValueTextView;
    private TextView yValueTextView;
    private TextView zValueTextView;

    private boolean isStart = false;
    private static final String TAG = "MainActivity";
    private String startString = "START";
    private String stopString = "STOP";

    private SensorManager sensorManager;
    private Sensor accelerometer;

    DecimalFormat decimalFormat = new DecimalFormat("##0.00#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: Initializing sensor services");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "onCreate: Registered accelerometer listener");


        final Button executionBtn = (Button)findViewById(R.id.executionBtn);
        xValueTextView = (TextView)findViewById(R.id.xValueTextView);
        yValueTextView = (TextView)findViewById(R.id.yValueTextView);
        zValueTextView = (TextView)findViewById(R.id.zValueTextView);

        executionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonName = executionBtn.getText().toString();
                if(buttonName.equals(startString)){
                    isStart = true;
                    executionBtn.setText(stopString);
                    executionBtn.setBackgroundColor(getResources().getColor((R.color.colorOfStop)));
                } else {
                    isStart = false;
                    executionBtn.setText(startString);
                    executionBtn.setBackgroundColor(getResources().getColor((R.color.colorOfStart)));
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(isStart) {
            xValueTextView.setText(decimalFormat.format(sensorEvent.values[0]) + "");
            yValueTextView.setText(decimalFormat.format(sensorEvent.values[1]) + "");
            zValueTextView.setText(decimalFormat.format(sensorEvent.values[2]) + "");
        } else {
            xValueTextView.setText("N/A");
            yValueTextView.setText("N/A");
            zValueTextView.setText("N/A");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
