package ari.ins.asi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;




import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import ari.ins.asi.R;

public class PhotosFragment extends Fragment {
	
	public PhotosFragment(){}
	
	
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	ListView  lw;
	
	Button  b,b1;
	
	SQLiteDatabase   db ;
	
	TextView name,description;  // Text view to populate title and description 
	TextToSpeech ttobj;
	Fragment fragment;
	   int  monument_id=2;
	   
	   //ArrayList<PointofInterestClass> pList = new ArrayList<PointofInterestClass>();
	   
	   ArrayList  plist=  new  ArrayList();


   
    
     ArrayList  list  =new ArrayList();
     
    
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		GlobalClass	globalVariable = (GlobalClass) getActivity().getApplicationContext();
		monument_id =globalVariable.getMonument_ID();
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        
        name=(TextView) rootView.findViewById(R.id.mname);
        description=(TextView) rootView.findViewById(R.id.description);
        
      
        db =getActivity().openOrCreateDatabase("Tapnknow",1, null);
        
        
        plist.clear();
        
     
        prepareListData();
        getData();
 	     
      lw=(ListView) rootView.findViewById(R.id.list);
      
        ArrayAdapter  obj  =new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_row, R.id.Itemname,plist);
        
       			lw.setAdapter(obj);
       			
				lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        public void onItemClick(AdapterView<?> av, View view, int position, long l) {
		        
		        	 final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
		        	 
		        	 globalVariable.setID(position);
		        	 System.out.println("list view position"+position);
		        	//String  name= (((TextView) view).getText()).toString();
		        	 
		        	 Object item = lw.getItemAtPosition(position);
		        	String  name=item.toString();
		         globalVariable.setName(name);
		            
		        	 FragmentManager fragmentManager = getFragmentManager();
		       			fragmentManager.beginTransaction()
		       					.replace(R.id.frame_container, new FindPeopleFragment()).addToBackStack(null).commit();
		        	
		        }
		    });
        
           
        return rootView;
    }
	
		  

	   
	   
	   public void   getimage()
	   {
		   Cursor cursor;
		   
		   byte[] image;
			 
			 String query ="select *  from  MonumentTable  where  id  ="+monument_id ;
			 cursor= db.rawQuery(query,null);
			 
			 if (cursor != null ) {
		            if  (cursor.moveToFirst()) {
		                  do {
		image= cursor.getBlob(3);
		                 
		                
		                  }while (cursor.moveToNext());
		            }
		       }
		   
	   }
	   
	   
	  
	   //   get  Name and  Tittle
	   
	   private void getData() {
			
			Cursor cursor;
			      
				 String query ="select * from  MonumentTable where  id ="+monument_id ;
				 cursor= db.rawQuery(query,null);
				 
				 if (cursor != null ) {
			            if  (cursor.moveToFirst()) {
			                  do {
			               
			
		String Titile = cursor.getString(cursor.getColumnIndex("Brief")).toString();
		
	String  Description = cursor.getString(cursor.getColumnIndex("Description")).toString();
		name.setText(Titile);
		description.setText(Description);
			
			      
			                  }while (cursor.moveToNext());
			            }
			       }
		
		}
		
	


	
	private void prepareListData() {
		
		Cursor cursor;
		      
			 String query ="select Brief from PointOfInterest where  id ="+monument_id ;
			 cursor= db.rawQuery(query,null);
			 
			 if (cursor != null ) {
		            if  (cursor.moveToFirst()) {
		                  do {
		               
		
		plist.add(cursor.getString(cursor.getColumnIndex("Brief")).toString());
		
		      
		                  }while (cursor.moveToNext());
		            }
		       }
	
	}
	
	
	public void onBackPressed() {
	// TODO Auto-generated method stub
		 fragment =new HomeFragment();
		 FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
	
	}
	
	
}
