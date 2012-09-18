package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockFragmentActivity;

public class DetailActivity extends RoboSherlockFragmentActivity {
	private CallingViewItem callingViewItem;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.callingdetail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		callingViewItem = (CallingViewItem)bundle.get("callingViewItems");
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
}