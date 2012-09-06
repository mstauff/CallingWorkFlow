package org.lds.community.CallingWorkFlow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.task.SyncCallingsTask;
import org.lds.community.CallingWorkFlow.task.WardListUpdateTask;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockPreferenceActivity;
import roboguice.inject.InjectView;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 9/6/12
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends RoboSherlockPreferenceActivity{

    @InjectView(R.id.pref_sync_callings_now)
    Preference syncCallingsPref;

    @InjectView(R.id.pref_sync_all_now)
    Preference syncAllPref;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncCallingsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                     public boolean onPreferenceClick(Preference preference) {
                         syncCallings();
                         return true;
                     }
                 });
        syncAllPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                     public boolean onPreferenceClick(Preference preference) {
                         syncAll();
                         return true;
                     }
                 });

    }

    public void syncAll() {
        WardListUpdateTask updateTask = new WardListUpdateTask( this, true );
        updateTask.execute();

    }

    public void syncCallings() {
        // todo - need to add a progress dialog
        SyncCallingsTask syncTask = new SyncCallingsTask(this);
        syncTask.execute( getString( R.string.calling_update_success ), getString(R.string.calling_update_failure));
    }


}