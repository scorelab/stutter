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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Fragment {

	TextView textview;
	
	
		/*super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//Auditory audio = new Auditory();
		textview = (TextView)findViewById(R.id.textview);
        String hello = stringFromJNI();
        textview.setText(hello);
        //new AlertDialog.Builder(this).setMessage(hello).show();
	}*/

	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	View home = inflater.inflate(R.layout.activity_home,container,false);
    	textview = (TextView)home.findViewById(R.id.textview);
		return home;
	}

	public native String stringFromJNI();
	
	static {
	    System.loadLibrary("daf");
	  }
}
