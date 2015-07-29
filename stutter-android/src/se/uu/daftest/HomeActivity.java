package se.uu.daftest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Fragment {

	Button recordButton;
	Button playButton;
	Thread recordThread;
	Thread playThread;
		
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	View home = inflater.inflate(R.layout.activity_home,container,false);
    	
    	createEngine();
    	createBufferQueueAudioPlayer();
    	
    	recordThread = new Thread(){
    		public void run() {
				setPriority(Thread.MAX_PRIORITY);
				startRecording();
			}
    	};
    	
    	playThread = new Thread(){
    		public void run() {
				setPriority(Thread.MAX_PRIORITY);
				selectClip(4);
			}
    	};
    	
    	recordButton 	= (Button)home.findViewById(R.id.recordButton);
    	playButton		= (Button)home.findViewById(R.id.playButton);
    	
    	recordButton.setOnClickListener(new OnClickListener() {
			Boolean created = false;
			@Override
			public void onClick(View v) {
				//call native method
				if(!created){
					created = createAudioRecorder();
					
				}
				if(created){
					recordThread.start();
				}
			}
		});
    	
    	
    	
    	playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playThread.start();
			}
		});
    	
		return home;
	}
    

    //call to invoke native method
    public static native void createEngine();
    public static native void createBufferQueueAudioPlayer();
	public static native boolean createAudioRecorder();
	public static native void startRecording();
	public static native void selectClip(int which);

	//Reference to the C library
	static {
	    System.loadLibrary("daf");
	  }
}
