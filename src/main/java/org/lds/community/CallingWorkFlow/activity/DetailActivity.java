package org.lds.community.CallingWorkFlow.activity;

import android.os.Bundle;
import android.view.View;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockFragmentActivity;

public class DetailActivity extends RoboSherlockFragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.callingdetail);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void saveCalling(View v){

    }

    public void cancelChanges(View v){

    }
}