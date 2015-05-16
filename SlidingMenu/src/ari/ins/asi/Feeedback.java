package ari.ins.asi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Feeedback extends Fragment {
	
	public Feeedback()
	{
		
	}
	
	Button  b;
	
EditText  txt_suggest, txt_name,  txt_email;
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
					
		 View rootView = inflater.inflate(R.layout.fragment_feddback_main, container, false);
		 
		 
		 b  = (Button) rootView.findViewById(R.id.btn_submit);
		 
		 txt_suggest=(EditText) rootView.findViewById(R.id.txt_suggest);
		 txt_name=(EditText) rootView.findViewById(R.id.txt_name);
		 txt_email=(EditText) rootView.findViewById(R.id.txt_email);

	       b.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Myclass obj  =new Myclass();
	            	obj.execute();
	            	
	            	
	        	}
	            
	        });
         
	        return rootView;
	        
	        
	       
	        
	        
	        
	     
	 }
	 
	 
     public class Myclass extends AsyncTask<Void, Void, Void>
       {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			
			
			
			String name = txt_name.getText().toString();
			String  sugest  = txt_suggest.getText().toString();
			String  Email =txt_email.getText().toString();
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://tapnknow.esy.es/email.php");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("sugest", sugest));
			pairs.add(new BasicNameValuePair("email", Email));
			pairs.add(new BasicNameValuePair("name", name));
			
			
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.execute(post);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
      	 
       }
      
	
	 
	 
	   
	 
	 

}
