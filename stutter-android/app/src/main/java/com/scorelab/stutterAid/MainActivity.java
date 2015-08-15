package com.scorelab.stutteraid;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int buttonPressed=0;
    int isFirst=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.loadLibrary("FrequencyDomain");
        DAF daf = new DAF();
        final Thread thread =  new Thread(daf);
        thread.setPriority(Thread.MAX_PRIORITY);

        final Button btn =(Button)findViewById(R.id.btn_start);
        final Button testBtn = (Button)findViewById(R.id.btn_test_activity);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonPressed==1){
                    buttonPressed=0;
                    btn.setText("start");
                    StopRecord();
                }
                else {

                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                    if (am.isBluetoothA2dpOn() || am.isWiredHeadsetOn()) {
                        btn.setText("stop");
                        buttonPressed = 1;
                        if (isFirst==0){
                            StartRecord();
                        }
                        else{
                            isFirst=0;
                            thread.start();
                        }

                        System.out.println(thread.getPriority());
                    } else {
                        Toast.makeText(getApplicationContext(), "Plug in headset",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestDataActivity.class);
                startActivity(intent);
            }
        });



    }

    public void startRecording(){

        // Get the device's sample rate and buffer size to enable low-latency Android audio output, if available.
        String samplerateString = null, buffersizeString = null;
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            samplerateString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            buffersizeString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
        }
        if(samplerateString==null) samplerateString= "44100";
        if(buffersizeString==null) buffersizeString = "128";

        System.loadLibrary("FrequencyDomain");
        FrequencyDomain(Integer.parseInt(samplerateString), Integer.parseInt(buffersizeString));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private native void FrequencyDomain(long samplerate, long buffersize);
    private native void StopRecord();
    private native void StartRecord();

    public class DAF implements Runnable{

        public DAF(){
            // android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        }

        @Override
        public void run() {
            startRecording();
        }
    }
}
