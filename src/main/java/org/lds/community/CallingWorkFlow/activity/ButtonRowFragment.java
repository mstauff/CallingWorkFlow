package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;

public class ButtonRowFragment  extends RoboSherlockFragment {

	@InjectView(value = R.id.addNewCallingBtn)
	Button newCallingBtn;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        newCallingBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCalling( view );
            }
        });
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
	    return getLayoutInflater( savedInstanceState ).inflate( R.layout.button_menu_row, container );
    }

	public void addNewCalling(View v){
		Intent intent = new Intent(getActivity(),DetailActivity.class);
		startActivity(intent);
	}
}