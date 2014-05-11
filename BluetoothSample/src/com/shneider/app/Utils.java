package com.shneider.app;

import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	// Message types sent from the BluetoothReadService Handler
    
    
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_PAIR_BT = 30;
    public static BluetoothAdapter mBluetoothAdapter = null;
    public static BluetoothDevice mBluetoothDevice = null;
    public static BluetoothSerialService mSerialService = null;
	public static BluetoothBroadcastReceiver btbr = null;
	
	public static void debugLog(Context context, String logMessage)
	{
		try
		{ 
			Log.i(Const.TAG, logMessage);
			if(context != null)
				Toast.makeText(context, logMessage, Toast.LENGTH_LONG).show();
		}
		catch (Exception e)
		{
			Log.i(Const.TAG, e.getMessage());
		}

	}
	
	
	 public static BluetoothDevice getPairedDevice(BluetoothAdapter _mBluetoothAdapter)
     {
     	BluetoothDevice res = null;
     	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
     	ArrayList<String> mPairedDevicesArrayAdapter = new ArrayList<String>();
     	// If there are paired devices
     	if (pairedDevices.size() > 0) {
     	    // Loop through paired devices
     	    for (BluetoothDevice device : pairedDevices) {
     	        // Add the name and address to an array adapter to show in a ListView
     	    	mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
     	    	if(device.getName().toLowerCase().contains(Const.DEVICE_NAME.toLowerCase()))
     	    	{
     	    		res = device;
     	    		break;
     	    	}
     	    }
     	}
 		return res;
     }

}
