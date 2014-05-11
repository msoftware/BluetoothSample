package com.shneider.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

//on boot complete
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Utils.debugLog(context, "BootReceiver: onReceive()");
		// TODO Auto-generated method stub
		Intent serviceIntent = new Intent(context, RegisterBroadcastReceiverService.class); 
		ComponentName cn =  context.startService(serviceIntent);
		if(cn != null)
		{
			Utils.debugLog(context, "RegisterBroadcastReceiverService starting in BootReceiver: " + cn.toString());
		}
		else
		{
			Utils.debugLog(context, "RegisterBroadcastReceiverService NOT started in BootReceiver");
		}
		

	}

}
