package com.scorelab.stutterAid;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class TestDataActivity extends AppCompatActivity {

    int isPressed=0;
    final String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
    MediaRecorder myAudioRecorder;
    Chronometer chronometer;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);

        final EditText et_filename = (EditText)findViewById(R.id.editText);
        chronometer=(Chronometer)findViewById(R.id.chronometer);


        final Button btnTest =(Button)findViewById(R.id.btn_start_test);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filename = et_filename.getText().toString();
                System.out.println(filename);
                if(isPressed==1){
                    isPressed=0;
                    btnTest.setText("Start");
                    chronometer.stop();
                    myAudioRecorder.stop();
                }
                else{
                    if(!filename.equals("")) {

                        isPressed = 1;
                        btnTest.setText("stop");
                        chronometer.start();
                        myAudioRecorder = new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(outputFile+"/"+filename+".mp3");
                        try {
                            myAudioRecorder.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        myAudioRecorder.start();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Give a file name",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_data, menu);
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
}
