package org.lds.community.CallingWorkFlow.tests.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.activity.StartupActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class StartupActivityTest \
 * org.lds.community.CallingWorkFlow.tests/android.test.InstrumentationTestRunner
 */
public class StartupActivityTest extends ActivityInstrumentationTestCase2<StartupActivity> {

    public StartupActivityTest() {
        super(StartupActivity.class);
    }

    @Test
    public void testThis() {
        assertTrue( true );
    }
}