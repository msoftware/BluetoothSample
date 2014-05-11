package com.shneider.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity{

	Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//android.os.Debug.waitForDebugger();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;	
		
	}
	
	@Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	
    	doInitSystem();
    	
    	doInitBluetooth();
    	
    }
	
	private void doStartService()
	{
		Intent serviceIntent = new Intent(this, RegisterBroadcastReceiverService.class); 
		ComponentName cn =  startService(serviceIntent);
		if(cn != null)
			Utils.debugLog(this, "RegisterBroadcastReceiverService starting in MainActivity onStart()");
		else
			Utils.debugLog(this, "RegisterBroadcastReceiverService NOT started in MainActivity onStart()");
	}

	
	private void doInitBluetooth()
	{
		if(Utils.mBluetoothAdapter != null)
    	{
	        if (!Utils.mBluetoothAdapter.isEnabled()) {
	        	Utils.debugLog(this, "Bluetooth not enabled");
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, Utils.REQUEST_ENABLE_BT);
	        // Otherwise, setup the chat session
	        }
	        else
	        {
	        	Utils.debugLog(this, "Bluetooth is enabled");
	        	/*Utils.mBluetoothDevice = Utils.getPairedDevice(Utils.mBluetoothAdapter);
	        	if(Utils.mBluetoothDevice == null)
	        	{
	        		pairDevice(null);
	        		Utils.mBluetoothAdapter.startDiscovery(); 
	        		findDeviceReceiver = new BroadcastReceiver() {
	        			public void onReceive(Context context, Intent intent) {
	        			    String action = intent.getAction();
	        			    if (BluetoothDevice.ACTION_FOUND.equals(action)) 
	        			    {
	        			        // Get the BluetoothDevice object from the Intent
	        			        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        			        if(device.getName().toLowerCase().contains(Const.DEVICE_NAME.toLowerCase()))
	        			        	registerToPair(device);
	        			        // Add the name and address to an array adapter to show in a ListView
	        			    }
	        			  }
	        			};

	        			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
	        			registerReceiver(findDeviceReceiver, filter);
	        		//askForPairing();
	        	}*/
	        	doStartService();
	        }
    	}		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case Utils.REQUEST_ENABLE_BT:
			{
				switch (resultCode)
				{
				case RESULT_OK:
					Utils.debugLog(this, "Bluetooth was enabled by user");
					doStartService();
					break;
				case RESULT_CANCELED:
					Utils.debugLog(this, "User did not enable Bluetooth!!!");
					break;
	
				default:
					break;
				}
			}
			break;

		default:
			break;
		}
		
	}

	private void doInitSystem()
	{
		Utils.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	/*
	private void registerToPair(final BluetoothDevice device)
	{
		BroadcastReceiver mReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
			    String action = intent.getAction();
			    if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(action)) 
			    {
			    	pairDevice(device);
			    }
			  }
			};

			IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"); 
			registerReceiver(mReceiver, filter);
			Method mm;
			try {
				mm = device.getClass().getMethod("createBond", (Class[]) null);
				try {
					mm.invoke(device, (Object[]) null);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}*/
	
	public void pairDevice(BluetoothDevice device) {
		/*byte[] pinBytes = new byte[]{0x00,0x00,0x00,0x00};//Utils.convertStringToBytes("0000");
	    try {
	          Log.d(Const.TAG, "Try to set the PIN");
	          Method m = device.getClass().getMethod("setPin", byte[].class);
	          m.invoke(device, pinBytes);
	          Log.d(Const.TAG, "Success to add the PIN.");
	          try {
	                device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
	                Log.d(Const.TAG, "Success to setPairingConfirmation.");
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                Log.e(Const.TAG, e.getMessage());
	                e.printStackTrace();
	            } 
	        } catch (Exception e) {
	          Log.e(Const.TAG, e.getMessage());
	          e.printStackTrace();
	        }*/
		
        String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
        Intent intent = new Intent(ACTION_PAIRING_REQUEST);
        String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
        intent.putExtra(EXTRA_DEVICE, device);
        String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";
        int PAIRING_VARIANT_PIN = 0;
        intent.putExtra(EXTRA_PAIRING_VARIANT, PAIRING_VARIANT_PIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

	
	
}
