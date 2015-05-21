package se.uu.daftest;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {


    static Map<String, Object> stateMap = new HashMap<String, Object>();

    DAFProcessor audio = null;
    Button buttonStart = null;
    Button buttonStop = null;
    TextView textStatus = null;
    SeekBar seekBarDelay = null;
    TextView textDelay = null;
    Button home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        home = (Button)findViewById(R.id.home);
		
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,HomeActivity.class);
				startActivity(intent);
			}
		});

        if (stateMap.containsKey("audio") == false) {
            audio = new DAFProcessor();
            stateMap.put("audio", audio);
        } else {
            audio = (DAFProcessor) stateMap.get("audio");
        }

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        textStatus = (TextView) findViewById(R.id.textViewStatus);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        if (audio.IsProcessing() == true) {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);
            textStatus.setText("Started");
        } else {
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            textStatus.setText("Stopped");
        }

        seekBarDelay = (SeekBar) findViewById(R.id.seekBarDelay);
        seekBarDelay.setOnSeekBarChangeListener(this);
        int progress = ((audio.GetDelay() - DAFProcessor.GetMinDealy()) * 100)
                        / (DAFProcessor.GetMaxDealy() - DAFProcessor.GetMinDealy());
        Log.i("Progress", "Progress : " + progress);
        seekBarDelay.setProgress(progress);

        textDelay = (TextView) findViewById(R.id.textViewDelay);
        textDelay.setText(Integer.toString(audio.GetDelay()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle stateToBeSaved) {
        if (audio != null) {
            stateMap.put("audio", audio);
        }
    }

    @Override
    public void onClick(View view) {

        int ID = view.getId();
        if (ID == buttonStart.getId()) {
            audio.StopProcessing();
            buttonStart.setEnabled(false);
            audio.StartProcessing();
            buttonStop.setEnabled(true);
            textStatus.setText("Started");

        } else if (ID == buttonStop.getId()) {
            audio.StopProcessing();
            buttonStop.setEnabled(false);
            buttonStart.setEnabled(true);
            textStatus.setText("Stopped");
        }

    }

    @Override
    public void onProgressChanged(SeekBar v, int progress, boolean isUser) {
        if (textDelay != null) {
            int delay = (progress * (DAFProcessor.GetMaxDealy() - DAFProcessor.GetMinDealy()) / 100)
                            + DAFProcessor.GetMinDealy();
            textDelay.setText(Integer.toString(delay));
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int delay = (seekBar.getProgress()
                        * (DAFProcessor.GetMaxDealy() - DAFProcessor.GetMinDealy()) / 100)
                        + DAFProcessor.GetMinDealy();
        audio.SetDelay(delay);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    
}
