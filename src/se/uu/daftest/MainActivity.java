package se.uu.daftest;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Fragment implements OnClickListener, OnSeekBarChangeListener {


    static Map<String, Object> stateMap = new HashMap<String, Object>();

    DAFProcessor audio = null;
    Button buttonStart = null;
    Button buttonStop = null;
    TextView textStatus = null;
    SeekBar seekBarDelay = null;
    TextView textDelay = null;
    Button home;
    

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	View main = inflater.inflate(R.layout.activity_main,container,false);
        

        if (stateMap.containsKey("audio") == false) {
            audio = new DAFProcessor();
            stateMap.put("audio", audio);
        } else {
            audio = (DAFProcessor) stateMap.get("audio");
        }

        buttonStart = (Button) main.findViewById(R.id.buttonStart);
        buttonStop = (Button) main.findViewById(R.id.buttonStop);
        textStatus = (TextView) main.findViewById(R.id.textViewStatus);

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

        seekBarDelay = (SeekBar) main.findViewById(R.id.seekBarDelay);
        seekBarDelay.setOnSeekBarChangeListener(this);
        int progress = ((audio.GetDelay() - DAFProcessor.GetMinDealy()) * 100)
                        / (DAFProcessor.GetMaxDealy() - DAFProcessor.GetMinDealy());
        Log.i("Progress", "Progress : " + progress);
        seekBarDelay.setProgress(progress);

        textDelay = (TextView) main.findViewById(R.id.textViewDelay);
        textDelay.setText(Integer.toString(audio.GetDelay()));
        
        return main;

    }


    @Override
	public void onSaveInstanceState(Bundle stateToBeSaved) {
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
