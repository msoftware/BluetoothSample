package com.shneider.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothHandler extends Handler {
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		
		switch (msg.what) {
        case Const.MESSAGE_STATE_CHANGE:
            
            switch (msg.arg1) {
            case BluetoothSerialService.STATE_CONNECTED:
            	Utils.debugLog(MyApplication.getAppContext(), "BluetoothHandler: BluetoothSerialService.STATE_CONNECTED");
                break;
            case BluetoothSerialService.STATE_CONNECTING:
            	Utils.debugLog(MyApplication.getAppContext(), "BluetoothHandler: BluetoothSerialService.STATE_CONNECTING");
                break;
            case BluetoothSerialService.STATE_LISTEN:
            	Utils.debugLog(MyApplication.getAppContext(), "BluetoothHandler: BluetoothSerialService.STATE_LISTEN");
            	break;
            case BluetoothSerialService.STATE_NONE:
            	Utils.debugLog(MyApplication.getAppContext(), "BluetoothHandler: BluetoothSerialService.STATE_NONE");
                break;
            }
            break;
        case Const.MESSAGE_WRITE:
            break;
        case Const.MESSAGE_READ:
            ///Get Bluetooth data
        	byte[] readBuf = (byte[]) msg.obj;
        	Log.i(Const.TAG, "BluetoothHandler: Bluetooth data: " + readBuf.toString());
            break;
        case Const.MESSAGE_DEVICE_NAME:
            break;
        case Const.MESSAGE_TOAST:
            break;
        }
	}

}
