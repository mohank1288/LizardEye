package com.lizard.eye;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import android.app.Application;
import android.content.SharedPreferences;
import com.lizard.eye.model.Model;
import com.lizard.eyes.R;

@ReportsCrashes(formKey = "", // will not be used
mailTo = "mohan.androidworks@gmail.com", customReportContent = {
		ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
		ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
		ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT }, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class LizardEyeApplication extends Application {

	public SharedPreferences mSharedPreferences;
	private static LizardEyeApplication sSingleton;

	// // The following line should be changed to include the correct property
	// id.
	// private final String PROPERTY_ID = "UA-51158678-1";
	//
	// //private int GENERAL_TRACKER = 0;
	//
	// public enum TrackerName {
	// APP_TRACKER, // Tracker used only in this app.
	// GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
	// // roll-up tracking.
	// ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
	// // company.
	// }
	//
	// private HashMap<TrackerName, Tracker> mTrackers = new
	// HashMap<TrackerName, Tracker>();
	//
	// public synchronized Tracker getTracker(TrackerName trackerId) {
	// if (!mTrackers.containsKey(trackerId)) {
	//
	// GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	// Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
	// .newTracker(PROPERTY_ID)
	// : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
	// .newTracker(R.xml.global_tracker) : analytics
	// .newTracker(R.xml.ecommerce_tracker);
	// mTrackers.put(trackerId, t);
	//
	// }
	// return mTrackers.get(trackerId);
	// }

	public LizardEyeApplication() {
		super();
	}


	public static LizardEyeApplication getSingleton() {
		return sSingleton;
	}

	@Override
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
		mSharedPreferences = getSharedPreferences(Model.getSingleton().SHARED_PREFS, 0);
		sSingleton = this;
	}

}
