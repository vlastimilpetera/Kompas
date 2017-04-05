package com.example.vlastik.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {



    ImageView imageViewArrow;
    private static SensorManager sensorManager;
    private Sensor sensor;
    private float aktualnipozice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewArrow = (ImageView) findViewById(R.id.imageViewArrow);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause(){
        super.onPause();;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int stupne = Math.round(event.values[0]);
        stupne-=90; //korekce směru šipky, naše míří doprava, tj, otočit o 90° stupnu
        RotateAnimation rotate = new RotateAnimation(
                aktualnipozice,
                -stupne,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(1000); //delka animace otaceni
        rotate.setFillAfter(true); //otočit a zůstat v nove pozici
        imageViewArrow.startAnimation(rotate); //otocime obrazkem
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }
}
