package org.lds.community.CallingWorkFlow.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import javax.inject.Inject;

public class CallingListActivity extends RoboActivity implements AdapterView.OnItemSelectedListener
{
    WorkFlowDB db;
    /** Called when the activity is first created. */
    @InjectView(R.id.callingsList)
    ListView callingsListView;

    @Inject
    CwfNetworkUtil networkUtil;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i( "CallingListActivity", "in create" );
        setContentView(R.layout.main);
        Spinner callingGroupsSpinner = (Spinner) findViewById( R.id.callingGroupsSpinner);
        callingGroupsSpinner.setOnItemSelectedListener(this);
        db = new WorkFlowDB( getApplicationContext() );
        Log.i( "CallingListActivity", "exit create" );
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if( parent.getId() == R.id.callingGroupsSpinner ) {
            // lookup new class
            //ListView classMembersList = (ListView) findViewById( R.id.classMembersList );
            Toast infoToast = Toast.makeText( getApplicationContext(), "Changed to " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT );
            infoToast.show();

        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCallingList(View view) {
        Spinner callingGroupsSpinner = (Spinner) findViewById( R.id.callingGroupsSpinner );
        String className = (String) callingGroupsSpinner.getSelectedItem();

    }

    public void addNewCalling(View view) {
        Toast infoToast = Toast.makeText( getApplicationContext(), "You clicked the + button", Toast.LENGTH_SHORT );
        infoToast.show();

    }
    public void removeCalling(View view) {
        Toast infoToast = Toast.makeText( getApplicationContext(), "You clicked the - button", Toast.LENGTH_SHORT );
        infoToast.show();

    }
}
