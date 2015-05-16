package ari.ins.asi;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Toast;
import ari.ins.asi.R;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	
	ImageView  img;
	ImageButton img2;
	Fragment fragment,fragment2;
	Button b;
	EditText editText;
	String pid = null;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        img=(ImageView) rootView.findViewById(R.id.imageView1);
        img2=(ImageButton) rootView.findViewById(R.id.imageButton1);
        b=(Button) rootView.findViewById(R.id.pbutton2);
        editText = (EditText) rootView.findViewById(R.id.editText1);
        
        b.setOnClickListener(new OnClickListener() {
       	 GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
       	 	 @Override
       	 public void onClick(View vw) {
       		int monument_id;
       		String poiName;
       		pid = editText.getText().toString(); 
       		
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
        
        
        
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	
            	startActivity(i);
              
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

	
	
	
	
	
}
