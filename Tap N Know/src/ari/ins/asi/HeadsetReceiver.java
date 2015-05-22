package ari.ins.asi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HeadsetReceiver  extends  BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
	            int state = intent.getIntExtra("state", -1);
	            GlobalClass globalVariable =(GlobalClass) context.getApplicationContext();
	            switch (state) {
	            case 0:
	              
	              globalVariable.set_headset(0);
	              
	            case 1:
	            	
	            	globalVariable.set_headset(1);
	             
	            default:
	               
	            }
	        }
		
	}

}
