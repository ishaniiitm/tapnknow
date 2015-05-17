package ari.ins.asi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ari.ins.asi.R;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	
	ImageView  img,img_asi;
	ImageButton img2;
	Fragment fragment,fragment2;
	Button b;
	EditText editText;
	TextView  txt_lang;
	String pid = null;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        img=(ImageView) rootView.findViewById(R.id.img_minuments);
        img_asi=(ImageView) rootView.findViewById(R.id.img_Asi);
        
        
       // img2=(ImageButton) rootView.findViewById(R.id.imageButton1);
        b=(Button) rootView.findViewById(R.id.btn_play);
        editText = (EditText) rootView.findViewById(R.id.editText1);
        
        
        GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        
     // Toast.makeText(getActivity().getApplicationContext(), globalVariable.getLanguage(), 1) .show();
        
        b.setOnClickListener(new OnClickListener() {
       	
        	 GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
       	 
       	 	 @Override
       	 public void onClick(View vw) {
       		int monument_id;
       		String poiName;
       		pid = editText.getText().toString(); 
       		
       		
       		
       		//Get I From  DislayID
       		
       		getDisplayID();
       		
       		System.out.println("pid in home fragment after input is : "+pid);
       		try{
       			SQLiteDatabase db  =getActivity().openOrCreateDatabase("Tapnknow", 1, null);
       			
       			Cursor c = db.rawQuery("Select id as ID, Brief as Name from PointOfInterest where pid = "+pid, null);
       			if(c.moveToFirst())
       			{
       				monument_id=c.getInt(c.getColumnIndex("ID"));
       				poiName = c.getString(c.getColumnIndex("Name"));
       				globalVariable.setMonument_ID(monument_id);
       				globalVariable.setID(Integer.parseInt(pid));
       				globalVariable.setName(poiName);
       				System.out.println("monumnet id in home fragment after input is"+monument_id);
       				System.out.println("pid in home fragment after input is"+pid);
       				System.out.println("poiname in home fragment after input is"+poiName);
       	       		
       	       		
       	       	 
       	       		 FragmentManager fragmentManager = getFragmentManager();
       	       			fragmentManager.beginTransaction()
       	       					.replace(R.id.frame_container, new FindPeopleFragment()).addToBackStack(null).commit();
       			}
       		}
       		catch(Exception ex){
       			
       			System.out.println(ex);       		};
       			
       		
       			
       			
       			
       		
       		 
       	 }
       	 });
        
        
        LayoutInflater inflator = LayoutInflater.from(getActivity().getBaseContext());
		View v = inflator.inflate(R.layout.abslayout, null);
		
		txt_lang  =(TextView) v.findViewById(R.id.txt_language);
		getActivity().getActionBar().setCustomView(v);
		
		
		txt_lang .setOnClickListener(new View.OnClickListener() {
            @Override
public void onClick(View v) {
            	
            	SingleChoiceWithRadioButton();
            
            }
            
        });
        
        
        
        img_asi.setOnClickListener(new View.OnClickListener() {
            @Override
public void onClick(View v) {
            	
            	fragment =new PhotosFragment();
        		final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        		
           	 globalVariable.setMonument_ID(4);
           	 
        		FragmentManager fragmentManager = getFragmentManager();
        		fragmentManager.beginTransaction()
        				.replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
        	}
            
        });
       
        
        
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	fragment =new PhotosFragment();
        		final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        		
           	 globalVariable.setMonument_ID(2);
           	 
        		FragmentManager fragmentManager = getFragmentManager();
        		fragmentManager.beginTransaction()
        				.replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
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
			      
			      fragment =new HomeFragment();
	        		
	           	 
	        		FragmentManager fragmentManager = getFragmentManager();
	        		fragmentManager.beginTransaction()
	        				.replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
	        		
	        		alert.dismiss();
			    	//Toast.makeText(getActivity().getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			    }
			});
			 alert = builder.create();
			alert.show();
		   }  
	
	
	
	 public void getDisplayID()
	 {
		 try{
    			SQLiteDatabase db  =getActivity().openOrCreateDatabase("Tapnknow", 1, null);
    	
    			
    			Cursor c = db.rawQuery("Select poiID from DisplayID where DisplayID = "+pid,null);
    			if(c.moveToFirst())
    			{
    				
    						String   id =c.getString(0);
    			        
    			            pid  =id;
    						System.out.println("id"+id);
    			
    	      
    	       	 
    	       	
    			}
    		}
    		catch(Exception ex){
    			
    			System.out.println(ex);       		};
    			
    		
    			
    			
    			
    		
    		 
    	 }
		 
	 }
	
	
	
	

