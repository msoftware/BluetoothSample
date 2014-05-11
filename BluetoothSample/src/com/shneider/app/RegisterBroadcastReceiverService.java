package com.shneider.app;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class RegisterBroadcastReceiverService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		// only when the service is 1st created
		Utils.debugLog(this, "RegisterBroadcastReceiverService: onCreate()");
		Intent in = doRegisterListener();
		if(in != null)
			Utils.debugLog(this, "BluetoothBroadcastReceiver registered: " + in.toString() );
		else
			Utils.debugLog(this, "BluetoothBroadcastReceiver NOT registered!");
		Utils.mBluetoothAdapter.startDiscovery();
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// each time the service is started/restarted
		super.onStartCommand(intent, flags, startId);
		Utils.debugLog(this, "RegisterBroadcastReceiverService: onStartCommand()");
		return START_STICKY;
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try
		{
			unregisterReceiver(Utils.btbr);
			Utils.debugLog(this, "Utils.btbr was un registered in RegisterBroadcastReceiverService:onDestroy()");
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "Utils.btbr was unable to unregistered in RegisterBroadcastReceiverService:onDestroy(): " + e.getMessage());
		}
	}
	
	private Intent doRegisterListener()
	{
		Intent res = null;
		try
		{
			Utils.btbr = new BluetoothBroadcastReceiver();
			    
		    IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
		    intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED);
		    intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED);
		    intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
		    intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_FOUND);
		    intentFilter.addAction(android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			intentFilter.addAction(android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED);//Dan 27-04
		    intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_BOND_STATE_CHANGED);//Dan 27-04
			
			res = registerReceiver(Utils.btbr, intentFilter);
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "Error in doRegisterListener() in RegisterBroadcastReceiverService:onDestroy(): " + e.getMessage());
		}
		return res;
	}

}
