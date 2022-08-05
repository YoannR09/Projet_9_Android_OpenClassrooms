package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();

        assertEquals("com.openclassrooms.realestatemanager.test", appContext.getPackageName());
    }

    @Test
    public void test() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        assertTrue(Utils.isNetworkAvailable(context));
    }



    public void disabledData() {
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("adb shell svc data disable");
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("adb shell svc wifi disable");
    }

    public void enableData() {
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("adb shell svc data enable");
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("adb shell svc wifi enable");
    }


}
