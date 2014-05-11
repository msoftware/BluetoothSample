package com.shneider.app;

public class Const {
	public static final String TAG = "SHNEIDER";
	public static boolean inDebug = true;
	
	// Message types sent from the BluetoothReadService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;	
    public static final String TOAST = "toast";
    
    public static final String KEY_DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_NAME = "my_device";

}
