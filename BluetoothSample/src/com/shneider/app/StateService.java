package com.shneider.app;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;

public class StateService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		// only when the service is 1st created
		Utils.debugLog(this, "StateService: onCreate()");
		try
		{
			Utils.mBluetoothAdapter.cancelDiscovery();
			lookForbluetoothDevice();
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "StateService: onCreate() crash: " + e.getMessage());
		}
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// each time the service is started/restarted
		super.onStartCommand(intent, flags, startId);
		Utils.debugLog(this, "StateService: onStartCommand()");
		if(intent != null)
		{
			String action = intent.getAction();
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if (action != null)
			{
				handleAction(device, intent, action);
			}
		}
		else
			Utils.debugLog(this, "StateService: onStartCommand() : intent is null");
		return START_STICKY;
		
	}
	
	private void handleAction(BluetoothDevice device, Intent intent, String action)
	{
		try
		{
			if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
	        {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
	                    BluetoothAdapter.ERROR);
				//Bluetooth adapter turned OFF/ON
	        	String stateStr = "";
	        	
				switch (state) {
				case BluetoothAdapter.STATE_OFF:
					stateStr = "Bluetooth off";
				break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					stateStr = "Turning Bluetooth off...";
				break;
				case BluetoothAdapter.STATE_ON:
					stateStr = "Bluetooth on";
				break;
				case BluetoothAdapter.STATE_TURNING_ON:
					stateStr = "Turning Bluetooth on...";
				break;
				}
				
				Utils.debugLog(this, "StateService: State changed to: " + stateStr);
	        }
			
	        String deviceName="";
	        
	        if(device !=null) 
	        {
	        	//Bluetooth connection changes
	        	deviceName = device.getName() + "\n" + device.getAddress();
				
				if (BluetoothDevice.ACTION_FOUND.equals(action))
		        {
					Utils.debugLog(this, "StateService: Device Found: " + deviceName);
		        }	        
		        //device pairing changes
		        else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action))
		        {
		        	Utils.debugLog(this, "StateService: ACTION_BOND_STATE_CHANGED");
		        	if (device.getBondState() == BluetoothDevice.BOND_BONDED)
		        	{
		        		Utils.debugLog(this, "StateService: BluetoothDevice.BOND_BONDED");
		        		lookForbluetoothDevice();
		        	}
		        	else if (device.getBondState() == BluetoothDevice.BOND_NONE)
		        	{
		        		Utils.debugLog(this, "StateService: BluetoothDevice.BOND_NONE");
		        	}
		        	else if (device.getBondState() == BluetoothDevice.BOND_BONDING)
		        	{
		        		Utils.debugLog(this, "StateService: BluetoothDevice.BOND_BONDING");
		        	}
		        }
		 
		        else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action))
		        {
		        	Utils.debugLog(this, "StateService: ACTION_ACL_CONNECTED");
		        	lookForbluetoothDevice();
		        }
		        else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action))
		        {
		        	Utils.debugLog(this, "StateService: ACTION_ACL_DISCONNECTED");

		        }
		        else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action))
		        {
		        	Utils.debugLog(this, "StateService: ACTION_ACL_DISCONNECT_REQUESTED");
		        }
		        else  if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
		        {
		        	Utils.debugLog(this, "StateService: ACTION_DISCOVERY_FINISHED");
		        }
	    		
	        }
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "StateService: handleAction crashed: " + e.getMessage());
		}
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try
		{
			Utils.debugLog(this, "StateService: onDestroy()");
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "StateService: onDestroy() crashed: " + e.getMessage());
		}
	}
	
	
	private void lookForbluetoothDevice()
	{
		try
		{
			Utils.mBluetoothDevice = Utils.getPairedDevice(Utils.mBluetoothAdapter);
	    	if(Utils.mBluetoothDevice != null)
	    	{
	    		Utils.debugLog(this, "Bluetooth device found: " + Utils.mBluetoothDevice.getName());
	    		Utils.mSerialService = new BluetoothSerialService(this, new BluetoothHandler());
	    		if(Utils.mSerialService != null)
	    		{
	    			Utils.debugLog(this, "Bluetooth connecting");
	    			Utils.mSerialService.connect(Utils.mBluetoothDevice);
	    		}
	    		else
	    		{
	    			Utils.debugLog(this, "BluetoothSerialService NOT initiated");
	    		}
	    	}
	    	else
	    	{	
	    		Utils.debugLog(this, "Bluetooth Device NOT found");
	    	}
		}
		catch(Exception e)
		{
			Utils.debugLog(this, "StateService lookForbluetoothDevice() crashed: " + e.getMessage());
		}
	}
	

}
