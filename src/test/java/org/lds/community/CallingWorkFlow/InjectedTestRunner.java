package org.lds.community.CallingWorkFlow;

import android.content.Context;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;
import roboguice.RoboGuice;


/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 9/14/12
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class InjectedTestRunner extends RobolectricTestRunner {
    public InjectedTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public void prepareTest(Object test) {
        Injector injector = RoboGuice.getInjector( Robolectric.getShadowApplication().getApplicationContext() );
        injector.injectMembers(test);
    }
}

