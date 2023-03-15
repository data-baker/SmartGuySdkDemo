package com.baker.smart.lib.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baker.smart.guy.lib.SmartGuyEngine;
import com.baker.smart.guy.lib.callback.SmartGuyEngineCallback;
import com.faceunity.ui.GLTextureView;

public class MainActivity extends AppCompatActivity {
    private GLTextureView mGlSurfaceView;
    private TextView tvQuestion, tvAnswer;
    private ImageView imgRecording;
    private String mVoiceName = "Beiling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

        initView();

        SmartGuyEngine.getInstance().initEngineView(mGlSurfaceView);
    }

    private void initView() {
        mGlSurfaceView = findViewById(R.id.gl_surface);
        tvQuestion = findViewById(R.id.tv_question);
        tvAnswer = findViewById(R.id.tv_answer);
        imgRecording = findViewById(R.id.img_recording);
    }

    public void onStartTalk(View view) {
        SmartGuyEngine.getInstance().startTalk(callback, mVoiceName);
    }

    public void onStopTalk(View view) {
        SmartGuyEngine.getInstance().stopTalk();
    }

    private SmartGuyEngineCallback callback = new SmartGuyEngineCallback() {
        @Override
        public void asrOnReadyOfSpeech() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvQuestion.setText("");
                    imgRecording.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void asrOnVolumeChanged(int volume) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    changeVolumeImg(volume);
                }
            });
        }

        @Override
        public void asrOnResult(String bestText, boolean isLast) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvQuestion.setText(bestText);
                    if (isLast){
                        imgRecording.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        @Override
        public void nlpOnAnswer(String answerText) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvAnswer.setText(answerText);
                }
            });
        }

        @Override
        public void onResumeByCloseEverything() {
            closeEveryThing();
        }

        @Override
        public void onError(String errorCode, String errorMsg) {
            log("errorCode = " + errorCode + ", errorMsg = " + errorMsg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "errorCode = "
                            + errorCode + ", errorMsg = " + errorMsg, Toast.LENGTH_SHORT).show();
                }
            });
            closeEveryThing();
        }
    };

    private void closeEveryThing() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvQuestion.setText("");
                tvAnswer.setText("");
                imgRecording.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void changeVolumeImg(int volume) {
        if (volume < 30) {
            imgRecording.setImageResource(R.mipmap.volume_1);
        } else if (volume < 40) {
            imgRecording.setImageResource(R.mipmap.volume_2);
        } else if (volume < 50) {
            imgRecording.setImageResource(R.mipmap.volume_3);
        } else if (volume < 60) {
            imgRecording.setImageResource(R.mipmap.volume_4);
        } else if (volume < 70) {
            imgRecording.setImageResource(R.mipmap.volume_5);
        } else if (volume < 80) {
            imgRecording.setImageResource(R.mipmap.volume_6);
        } else if (volume < 90) {
            imgRecording.setImageResource(R.mipmap.volume_7);
        } else {
            imgRecording.setImageResource(R.mipmap.volume_8);
        }
    }

    private void log(String text) {
        Log.e("MainActivity", text);
    }

    /**
     * 只有3个资源，故取值0-1-2
     * 0 = 红衣服小姐姐
     * 1 = 卡通女
     * 2 = 卡通男
     */
    private int effectIndex = 0;
    public void switchBundle(View view) {
        effectIndex++;
        if (effectIndex==2){
            effectIndex = 0;
        }
        SmartGuyEngine.getInstance().switchBundle(effectIndex);
        //切换形象的同时，设置合成的声音音色
        switch (effectIndex){
            case 0:
                SmartGuyEngine.getInstance().setTtsVoiceName("Beiling");
                break;
            case 1:
                SmartGuyEngine.getInstance().setTtsVoiceName("Beiling");
                break;
            case 2:
                SmartGuyEngine.getInstance().setTtsVoiceName("Beiling");
                break;
        }
    }

    public void onStopTask(View view) {
        SmartGuyEngine.getInstance().stopTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartGuyEngine.getInstance().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SmartGuyEngine.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SmartGuyEngine.getInstance().onPause();
    }
}