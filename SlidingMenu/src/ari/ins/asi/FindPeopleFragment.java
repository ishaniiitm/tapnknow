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
	TextView txt_name,txt_brief,txt_Story,txt_arch,txt_myth;
	TextToSpeech ttobj;
	Button  btn_story,btn_arch,btn_myth, btn_play,btn_stop ,btn_pause;
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
        
        
        txt_name= (TextView) rootView.findViewById(R.id.txt_name);
        txt_brief =(TextView) rootView.findViewById(R.id.txt_Brief);
        txt_Story=(TextView) rootView.findViewById(R.id.txt_story);
       txt_arch=(TextView) rootView.findViewById(R.id.txt_arch);
       txt_myth=(TextView) rootView.findViewById(R.id.txt_myth);
      //  t=(TextView) rootView.findViewById(R.id.Heading);
        btn_story=(Button) rootView.findViewById(R.id.btn_story);
        btn_arch=(Button) rootView.findViewById(R.id.btn_arch);
       btn_myth=(Button) rootView.findViewById(R.id.btn_myth);
       
       btn_play=(Button) rootView.findViewById(R.id.btn_play);
       btn_pause=(Button) rootView.findViewById(R.id.btn_pause);
       btn_stop =(Button) rootView.findViewById(R.id.btn_stop);
        
     /*  RelativeLayout  rl =(RelativeLayout) rootView .findViewById(R.id.droppanel);
       RelativeLayout  r2 =(RelativeLayout) rootView .findViewById(R.id.droppanel1);
       RelativeLayout  r3 =(RelativeLayout) rootView .findViewById(R.id.droppanel2);
      */
   // defining global variables
        GlobalClass globalvariable  = (GlobalClass)getActivity().getApplicationContext();
      name=globalvariable.getName();
     monument_ID= globalvariable.getMonument_ID();			
       pid = globalvariable.getid();
       
        System.out.println(name);
        
        try
        {
        	// 
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
         
        
      
        
      btn_story. setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             
            	   if(txt_Story.getVisibility()==View.GONE)
                   {
            		   txt_Story.setVisibility(View.VISIBLE);

                   }
            	   else
            	   {
            		   txt_Story.setVisibility(View.GONE );
            	   }

            	
            }
        });
       
       
       btn_arch. setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	   if(txt_arch.getVisibility()==View.GONE)
                  {
           		txt_arch.setVisibility(View.VISIBLE);

                  }
           	   else
           	   {
           		txt_arch.setVisibility(View.GONE
           				   );
           	   }

           	
           }
       });
       
       btn_myth. setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	   if(txt_myth.getVisibility()==View.GONE)
                  {
           		txt_myth.setVisibility(View.VISIBLE);

                  }
           	   else
           	   {
           		txt_myth.setVisibility(View.GONE
           				   );
           	   }

           	
           }
       });
       
       // Onclick Function  of  Play button
       
       
     btn_play.setOnClickListener(new View.OnClickListener(){
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
       
       
       btn_pause.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            
           	ttobj.shutdown();

           	
           }
       });
       
       
 // Onclick Function  of  Stop Button
       
       
       btn_stop.setOnClickListener(new View.OnClickListener(){
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
		  
	       txt_name.setText(name);
	 }
	 
	 
	 public void   getContent()
	 {
		 Cursor c;
		 String firstName = null;
		 System.out.println("pid under get content function"+pid);
		
		 
		 String query =null;
		 String query2 = null;
		 String temp=null;
		//Setting text in Story
		 
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 1";
		 query2 = "select descpName from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 1";
		 c= db.rawQuery(query2, null);
			
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("descpName"));
			 query2 = "select Type from Description_Type_Master where ID ="+temp;
			 c= db.rawQuery(query2, null);
			 if(c.moveToFirst())
			 {
				 temp = c.getString(c.getColumnIndex("Type"));
				 btn_story.setText(temp);
				 
			 }
			 
		 }
		 
		 
		 c= db.rawQuery(query, null);
		
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			 txt_Story.setText(temp);
			 
		 }
		 
		 
 ttsString=	ttsString+" "+temp.toString();
		 System.out.println(temp);
		
		 
		 //Setting text in Architecture
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 2";
		 query2 = "select descpName from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 2";
		 

		 c= db.rawQuery(query2, null);
			
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("descpName"));
			 query2 = "select Type from Description_Type_Master where ID ="+temp;
			 c= db.rawQuery(query2, null);
			 if(c.moveToFirst())
			 {
				 temp = c.getString(c.getColumnIndex("Type"));
				 btn_arch.setText(temp);
				 
			 }
			 
		 }

		 
		 c= db.rawQuery(query, null);
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			txt_arch.setText(temp);
			 
		 }
		 
		 
		 ttsString=	ttsString+" "+temp.toString();
		 System.out.println(temp);
		 //Setting text in Myth
		 query = "select descpt from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 3";
		
 query2 = "select descpName from POIDescpTable where ID="+monument_ID+" AND PID="+pid+" AND DescpTYpe= 3";
		 

		 c= db.rawQuery(query2, null);
			
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("descpName"));
			 query2 = "select Type from Description_Type_Master where ID ="+temp;
			 c= db.rawQuery(query2, null);
			 if(c.moveToFirst())
			 {
				 temp = c.getString(c.getColumnIndex("Type"));
				 btn_myth.setText(temp);
				 
			 }
			 
		 }
		 c= db.rawQuery(query, null);
		 if(c.moveToFirst())
		 {
			 temp = c.getString(c.getColumnIndex("Descpt"));
			txt_myth.setText(temp);
			 
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


