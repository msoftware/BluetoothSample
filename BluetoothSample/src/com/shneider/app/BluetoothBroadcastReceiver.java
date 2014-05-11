package com.shneider.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//android.os.Debug.waitForDebugger();
		//start StateService with current intent
		try
		{
			if((context != null) && (intent != null))
			{
				
				Intent serviceIntent = new Intent(intent);
				serviceIntent.setClass(context, StateService.class);
				ComponentName cn =  context.startService(serviceIntent);
				if(cn != null)
				{
					Utils.debugLog(context, "Service 'StateService' starting from BluetoothBroadcastReceiver");
				}
				else
				{
					Utils.debugLog(context, "Service 'StateService' NOT started from BluetoothBroadcastReceiver");
				}
				
			}
			
		}
		catch(Exception e)
		{
			Utils.debugLog(context, "Service 'StateService' crashed on BluetoothBroadcastReceiver: " + e.getMessage());
		}
        
	}

}
