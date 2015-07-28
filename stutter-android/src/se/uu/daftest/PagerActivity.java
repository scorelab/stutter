package se.uu.daftest;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class PagerActivity extends FragmentActivity {
	
	ViewPager tab;
	TabPagerAdapter tabPagerAdapter;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);
		
		tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
		tab = (ViewPager)findViewById(R.id.pager);
		tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar = getActionBar();
                actionBar.setSelectedNavigationItem(position); 
			}
			
			
		});
		
		 tab.setAdapter(tabPagerAdapter);
		 actionBar = getActionBar();
		 actionBar.setTitle("Stutter Aid");
		 actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(ActionBar.Tab aTab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(ActionBar.Tab aTab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				tab.setCurrentItem(aTab.getPosition());
				
			}
			
			@Override
			public void onTabReselected(ActionBar.Tab aTab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		actionBar.addTab(actionBar.newTab().setText("Java").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("OpenSL").setTabListener(tabListener));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
