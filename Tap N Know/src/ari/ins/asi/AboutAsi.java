package ari.ins.asi;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutAsi extends Fragment {
	public AboutAsi()
	{
		
	}
	Fragment fragment;;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_aboutasi, container, false);
         
       
        TextView txt_lng;
        
        LayoutInflater inflator = LayoutInflater.from(getActivity().getBaseContext());
		View v = inflator.inflate(R.layout.abslayout, null);
		
		txt_lng  =(TextView) v.findViewById(R.id.txt_language);
		getActivity().getActionBar().setCustomView(v);
		
		
		txt_lng .setOnClickListener(new View.OnClickListener() {
            @Override
public void onClick(View v) {
            	
            	SingleChoiceWithRadioButton();
            
            }
            
        });
		 return rootView;
        
    }
	
	
	
	AlertDialog alert;	
	 private void SingleChoiceWithRadioButton() {  
		
		 final CharSequence[] items = {"English"};
			 
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Pick a Language");
			
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	   
			        GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
			        
			      globalVariable.setLanguage(items[item].toString());
			      
			   
	        		alert.dismiss();
			    	//Toast.makeText(getActivity().getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			    }
			});
			 alert = builder.create();
			alert.show();
		   }  
	 
	 public void onBackPressed() {
			// TODO Auto-generated method stub
				 fragment =new HomeFragment();
				 FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
			
			}
	


}
