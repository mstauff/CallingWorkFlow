package org.lds.community.CallingWorkFlow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;

public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener
{
    WorkFlowDB db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i( "MyActivity", "in create" );
        setContentView(R.layout.main);
        Spinner classSpinner= (Spinner) findViewById( R.id.classSpinner );
        classSpinner.setOnItemSelectedListener( this );
        db = new WorkFlowDB( getApplicationContext() );
        Log.i( "MyActivity", "exit create" );
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if( parent.getId() == R.id.classSpinner ) {
            // lookup new class
//            ListView classMembersList = (ListView) findViewById( R.id.classMembersList );
            Toast infoToast = Toast.makeText( getApplicationContext(), "Changed to " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT );
            infoToast.show();

        } else if (parent.getId() == R.id.dateSpinner ) {
            // lookup or create the new date
        }

    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRoster(View view) {
        Spinner classSpinner = (Spinner) findViewById( R.id.classSpinner );
        String className = (String) classSpinner.getSelectedItem();

    }
}
