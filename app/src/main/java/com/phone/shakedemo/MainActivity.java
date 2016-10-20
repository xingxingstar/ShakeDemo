package com.phone.shakedemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    SensorManager manager;
    SensorEventListener eventListener;
    Vibrator vibrator;
    private Sensor sensor;
    SoundPool soundPool;
    private int soundId;
    ImageView imageUp;
    ImageView imageDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageUp = (ImageView) findViewById(R.id.imageUp);
        imageDown = (ImageView) findViewById(R.id.imageDown);
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
//        参数1：maxStreams 最大流1-10
//        参数2：streamType 流的类型
//        参数3：srcQuality 无效，默认为0
        soundId = soundPool.load(MainActivity.this, R.raw.shake, 0);
//        参数2：resId 资源Id
//        参数3：priority 优先级，暂无效，默认0

//        参数1：传感器的类型
//        根据给定的type获取一个默认的传感器，但注意此传感器是处理之后，复合的传感器
//        用于接收传感器管理器中传感器变化的通知
        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];//获取x的移动位置
                float y = event.values[1];//获取y的移动位置
                float z = event.values[2];//获取z的移动位置
                if (Math.abs(x) > 19 || Math.abs(y) > 19 || Math.abs(z) > 19) {//判断是否摇手机
//                    vibrator.vibrate(200);//振动时长
                    soundPool.play(soundId, 0, 1, 0, -1, 1);
                    getAnimation();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        manager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//        注册给定的有一定速率的传感器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(eventListener, sensor);
    }

    public void getAnimation() {
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        translate.setDuration(3000);
        TranslateAnimation translate2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f);
        translate2.setStartOffset(1000);
        translate.setDuration(3000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        set.addAnimation(translate2);
        imageUp.startAnimation(set);

        TranslateAnimation translate3 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f);
        translate3.setDuration(3000);
        TranslateAnimation translate4 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        translate4.setStartOffset(1000);
        translate4.setDuration(3000);
        AnimationSet set1 = new AnimationSet(false);
        set1.addAnimation(translate3);
        set1.addAnimation(translate4);
        imageDown.startAnimation(set1);
    }
}
