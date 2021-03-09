package com.project.best.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private SensorManager sensorManager;
    private TextView sensorAccelerometerText;
    private TextView sensorProximityText;
//  Sensor
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText = findViewById(R.id.sensor_list);
        sensorAccelerometerText = findViewById(R.id.sensor_accelerometer);
        sensorProximityText = findViewById(R.id.sensor_proximity);
//        Untuk mendefinisikan sensor managernya
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        Mendefinisikan sensornya
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

//        untuk menyimpan list dari sensor yang ada
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorTx = new StringBuilder();
//        memasukan sensor sensor yang ada kedalam list
        for(Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+"\n");
        }
//        menampilkan list sensor
        sensorText.setText(sensorTx.toString());

//        mengecek apakah didalam hp ada sensor ini
        if (sensorAccelerometer == null){
            Toast.makeText(this, "Tidak Ada Sensor Accelerometer",
                    Toast.LENGTH_SHORT).show();
        }
        if (sensorProximity == null){
            Toast.makeText(this, "Tidak Ada Sensor Proximity",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorProximity != null){
            sensorManager.registerListener(this,
                    sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorAccelerometer != null){
            sensorManager.registerListener(this,
                    sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float value = event.values[0];
//        jika sensor typenya accelerometer
        if (sensorType == Sensor.TYPE_ACCELEROMETER){
//            Menampilkan hasil dari sensor accelerometer
            sensorAccelerometerText.setText(String.format("Accelerometer Sensor: %1$.2f", value));
        }
//        jika sensor typenya proximity
        if (sensorType == Sensor.TYPE_PROXIMITY){
//            Menampilkan hasil dari sensor accelerometer
            sensorProximityText.setText(String.format("Proximity Sensor: %1$.2f", value));
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
