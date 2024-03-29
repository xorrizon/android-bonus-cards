package net.xorrizon.bonuscards2;

import android.test.ActivityInstrumentationTestCase2;
import net.xorrizon.bonuscards2.ui.CardsActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class net.xorrizon.bonuscards2.ui.CardsActivityTest \
 * net.xorrizon.bonuscards2.tests/android.test.InstrumentationTestRunner
 */
public class CardsActivityTest extends ActivityInstrumentationTestCase2<CardsActivity> {

	public CardsActivityTest() {
		super("net.xorrizon.bonuscards2", CardsActivity.class);
	}

}
