package ari.ins.asi;

import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ari.ins.asi.R;

public class FindPeopleFragment extends Fragment {
	
/*	public FindPeopleFragment(int tPid){
		
		pid=tPid;
		
		System.out.println(pid);
	}
	*/
public FindPeopleFragment(){}
	
	//Variable Declaration
	TextView t, t1,t2,t4;
	TextToSpeech ttobj;
	Button  b1,b2,b3;
	SQLiteDatabase db;
	Cursor c;
	String sqlQuery;
	int  pid;					// Point of interest ID
	int monument_ID;			// Monument ID
	String name=null;
	String ttsString=null;
	String  language;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 //Variable Definition
        View rootView = inflater.inflate(R.layout.fragment_find_people, container, false);
        db  =getActivity().openOrCreateDatabase("Tapnknow", 1, null);
        t1=(TextView) rootView.findViewById(R.id.droptext);
        t2=(TextView) rootView.findViewById(R.id.droptext1);
        t4=(TextView) rootView.findViewById(R.id.droptext2);
        t=(TextView) rootView.findViewById(R.id.Heading);
        b1=(Button) rootView.findViewById(R.id.button1);
        b2=(Button) rootView.findViewById(R.id.button2);
        b3=(Button) rootView.findViewById(R.id.button3);
        
       RelativeLayout  rl =(RelativeLayout) rootView .findViewById(R.id.droppanel);
       RelativeLayout  r2 =(RelativeLayout) rootView .findViewById(R.id.droppanel1);
       RelativeLayout  r3 =(RelativeLayout) rootView .findViewById(R.id.droppanel2);
       GlobalClass globalvariable  = (GlobalClass)getActivity().getApplicationContext();
    // defining global variables
       name=globalvariable.getName();
       monument_ID= globalvariable.getMonument_ID();			
       pid = globalvariable.getid();
       
        System.out.println(name);
        
        try
        {
        	String query_getposition = "Select pid from PointOfInterest where Brief = "+"'"+name+"'";
            Cursor c = db.rawQuery(query_getposition, null);
            if(c.moveToFirst())
            {
            	
            	System.out.println("value from db"+c.getInt(c.getColumnIndex("pid")));
            	pid=c.getInt(c.getColumnIndex("pid"));
            }
                  System.out.println("value from global variable"+pid);
            System.out.println("list view position fetched."+pid);
          
   		 
         getname();
        getContent();
 
        }catch(Exception  ex)
        {
        	
        }
         
        
      
        
       rl. setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             
            	   if(t1.getVisibility()==View.GONE)
                   {
            		   t1.setVisibility(View.VISIBLE);

                   }
            	   else
            	   {
            		     t1.setVisibility(View.GONE
            				   );
            	   }

            	
            }
        });
       
       
       r2. setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	   if(t2.getVisibility()==View.GONE)
                  {
           		   t2.setVisibility(View.VISIBLE);

                  }
           	   else
           	   {
           		     t2.setVisibility(View.GONE
           				   );
           	   }

           	
           }
       });
       
       r3. setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	   if(t4.getVisibility()==View.GONE)
                  {
           		   t4.setVisibility(View.VISIBLE);

                  }
           	   else
           	   {
           		     t4.setVisibility(View.GONE
           				   );
           	   }

           	
           }
       });
       
       // Onclick Function  of  Play button
       
       
       b1.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
        	   ttobj=new TextToSpeech(getActivity().getApplicationContext(), 
         		      new TextToSpeech.OnInitListener() {
         		      @Override
         		      public void onInit(int status) {
         		         if(status != TextToSpeech.ERROR){
         		             ttobj.setLanguage(Locale.UK);
         		             
         		             GlobalClass  globalvariable = (GlobalClass) getActivity().getApplicationContext();
         		             
         		             language=globalvariable.getLanguage();
         		             speakText(language);
         		             
         		            }				
         		         }
         		      });

           	
           }
       });
       
 // Onclick Function  of  Pause
       
       
       b2.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	ttobj.shutdown();

           	
           }
       });
       
       
 // Onclick Function  of  Stop Button
       
       
       b3.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
        	   ttobj.shutdown();


           	
           }
       });
       
       // Event  to Intialize  Text to Speetch
       
    
       
         
        return rootView;
    }
	
	//Function For  Text To Speetch
	

	
	
	 public void  getname()
	 {
		  
	       t.setText(name);
	 }
	 
	 
	 public void   getContent()
	 {
		 Cursor c;
		 String firstName = null;
		 System.out.println("pid under get content function"+pid);
		
		 
		 String query =null;
		
		 String temp=null;
		//Setting text in Story
		 
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 1";
		 c= db.rawQuery(query, null);
		
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			 t1.setText(temp);
			 
		 }
 ttsString=	ttsString+" "+temp.toString();
		 System.out.println(temp);
		
		 //Setting text in Architecture
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 2";
		 c= db.rawQuery(query, null);
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			 t2.setText(temp);
			 
		 }
		 ttsString=	ttsString+" "+temp.toString();
		 System.out.println(temp);
		 //Setting text in Myth
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 3";
		 c= db.rawQuery(query, null);
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			 t4.setText(temp);
			 
		 }
		 ttsString=	ttsString+" "+temp.toString();
		 System.out.println(temp);
		 
		 
		 
	 }
	 
	  
		public void speakText(String  lng){
			
			System.out.println("speakText executed with language"+lng);
		      String toSpeak = ttsString;
		      System.out.println("speakText executed with text"+ttsString);
		      ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
		      

		   }
	  
	 
}


