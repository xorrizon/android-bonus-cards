package net.xorrizon.bonuscards2;

import android.app.Application;
import android.content.Context;

public class BonusCardsApplication extends Application {
	private static Context context;
	public void onCreate(){
		context=getApplicationContext();
	}

	public static Context getAppContext(){
		return context;
	}
}
