package org.cnc.nsv;

import org.cnc.nsv.activity.PushActivity;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;

public class Application extends android.app.Application implements Constant {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		 defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
		PushService.subscribe(this, "", PushActivity.class);
		PushService.setDefaultPushCallback(this, PushActivity.class);
		
		//ParseObject o = new ParseObject("Installation");
		//o.saveInBackground();
	}
}
