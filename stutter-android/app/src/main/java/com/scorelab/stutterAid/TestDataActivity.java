package com.scorelab.stutteraid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class TestDataActivity extends AppCompatActivity {

    int isPressed=0;
    final String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
    MediaRecorder myAudioRecorder;
    String filename;
    String uuid;
    int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);

        final TextView tv_state = (TextView)findViewById(R.id.textView3);
        final Button btnTest =(Button)findViewById(R.id.btn_start_test);
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        uuid = tManager.getDeviceId();
        Calendar c = Calendar.getInstance();
        seconds = c.get(Calendar.SECOND);
        filename =  uuid+seconds;

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == 1) {
                    isPressed = 0;
                    btnTest.setText("Start");
                    myAudioRecorder.stop();
                    Toast.makeText(getApplicationContext(), "File saved as " + filename + ".mp3",
                            Toast.LENGTH_LONG).show();
                    createAndShowAlertDialog();
                } else {


                    isPressed = 1;
                    btnTest.setText("stop");
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile + "/" + filename + ".mp3");
                    tv_state.setText("Recodring started....");
                    try {
                        myAudioRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAudioRecorder.start();
                }
            }
        });
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestDataActivity.this);
        builder.setTitle("File saved. Do you want to upload this to our servers?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                uploadFile();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadFile(){
        try {
            String fileName = outputFile + "/" + filename + ".mp3";
            File file = new File(outputFile + "/" + filename + ".mp3");
            UploadHandler uploadHandler = new UploadHandler(fileName);
            uploadHandler.execute("http://172.20.5.61:8080/uploadServer/upload.php");
        } catch (Exception e) {
            // show error
        }
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
