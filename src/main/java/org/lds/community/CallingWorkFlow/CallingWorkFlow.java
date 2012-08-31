package org.lds.community.CallingWorkFlow;

import android.app.Application;
import android.app.Activity;
import android.content.Intent;
import org.lds.community.CallingWorkFlow.activity.StartupActivity;
import org.lds.mobile.util.TagUtil;

public class CallingWorkFlow extends Application {
	public static final String TAG = CallingWorkFlow.createTag(CallingWorkFlow.class);
	private static Class currentRootClass;
	public static final String DEFAULT_TAG_PREFIX = "workflow.";

	public static String createTag(Class clazz) {
        return TagUtil.createTag(DEFAULT_TAG_PREFIX, clazz);
    }

	public static void startRootActivity(Class clazz, Activity activity) {
        currentRootClass = clazz;

        Intent intent = new Intent(activity, StartupActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
