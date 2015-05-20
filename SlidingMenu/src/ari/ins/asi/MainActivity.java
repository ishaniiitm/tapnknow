package ari.ins.asi;

import ari.ins.asi.adapter.NavDrawerListAdapter;
import ari.ins.asi.model.NavDrawerItem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ari.ins.asi.R;

public class MainActivity extends Activity  {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	NfcAdapter nfcAdapter;
	    public static final String MIME_TYPE = "application/ins";
GlobalClass globalVariable;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// navigation drawer items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ProgressDialog prgDialog;
// Progress Dialog type (0 - for Horizontal progress bar)
public static final int progress_bar_type = 1;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	Fragment fragment;
	
	TextView  txt_lang ;
	HeadsetReceiver  receiver;
	  //Copying database to device


	
	  private ProgressDialog pDialog;
      // Progress Dialog type (0 - for Horizontal progress bar)
 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		copyDatabase();
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
				//copying sqlite database on Android device
		
		 new Databasetransfer().execute("test");
		
		
		
		
 receiver =new HeadsetReceiver();
		//  add  listner
		
		
		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.abslayout, null);
		
		txt_lang  =(TextView) v.findViewById(R.id.txt_language);
		getActionBar().setCustomView(v);

txt_lang.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SingleChoiceWithRadioButton();
		
	}
});
	
		
		fragment  =new HomeFragment();
		
	//txt_lang=	(TextView) getActionBar().getCustomView().findViewById(R.id.textView1);
		
	getActionBar().setBackgroundDrawable(new  ColorDrawable(Color.parseColor("#0A81F7")));
		//getActionBar().setBackgroundDrawable(new  ColorDrawable(Color.parseColor(R.layout.header_gradient)));

	getActionBar().setBackgroundDrawable(
            getResources().getDrawable(R.drawable.gradiant)); 
	
	getActionBar().setIcon(
			   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
	
	
	
	int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
	if (actionBarTitleId > 0) {
	    TextView title = (TextView) findViewById(actionBarTitleId);
	    if (title != null) {
	        title.setTextColor(Color.WHITE);
	    }
	}

	
	defineNavDrawer();
	if (savedInstanceState == null) {
		// on first time display view for first nav item
		displayView(0);
	}	
	try{
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);

		if (nfcAdapter != null && nfcAdapter.isEnabled()) {
			
			//Toast.makeText(this, "NFc is available", Toast.LENGTH_LONG).show();
			Intent intent = getIntent();
			parseIntent(getIntent());

		} else {
			//Toast.makeText(this, "NFc is not available", Toast.LENGTH_LONG).show();
			
		}
	}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
		// checking for nfc
		
	}

	/**
	 * Slide menu item click listener
	 * */
	
	public void defineNavDrawer()
	{
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
				navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

				// nav drawer icons from resources
				navMenuIcons = getResources()
						.obtainTypedArray(R.array.nav_drawer_icons);

				mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
				mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

				navDrawerItems = new ArrayList<NavDrawerItem>();

				// adding nav drawer items to array
				// Home
				int i = 0;
				for(i=0;i<4;i++)
				{
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
				}
				// Recycle the typed array
				navMenuIcons.recycle();

				mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

				// setting the nav drawer list adapter
				adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
				mDrawerList.setAdapter(adapter);

				// enabling action bar app icon and behaving it as toggle button
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);
				getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
				
				
				getActionBar().setCustomView(R.layout.abslayout);
				
				mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
						R.drawable.lines, //nav menu toggle icon
						R.string.app_name, // nav drawer open - description for accessibility
						R.string.app_name // nav drawer close - description for accessibility
				) {
					public void onDrawerClosed(View view) {
						getActionBar().setTitle(mTitle);
						// calling onPrepareOptionsMenu() to show action bar icons
						invalidateOptionsMenu();
						
					}

					public void onDrawerOpened(View drawerView) {
						getActionBar().setTitle(mDrawerTitle);
						// calling onPrepareOptionsMenu() to hide action bar icons
						invalidateOptionsMenu();
					}
				};
				mDrawerLayout.setDrawerListener(mDrawerToggle);
			
	}
	
	
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			System.out.println("one two thereree");
			return true;
		}
		
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.txt_language:
			SingleChoiceWithRadioButton();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
	//	boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_lang).setVisible(false);
		
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new AboutUs();
			break;
		case 2:
			fragment = new  AboutAsi();
			break;
		case 3:
			//fragment = new CommunityFragment();
			fragment = new Feeedback();
			break;
		case 4:
			fragment = new Feeedback();
			break;
		case 5:
			//fragment = new WhatsHotFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	


	
	@Override
    protected void onResume() {
        
		 IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(receiver, filter);
		super.onResume();
        
        
        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();
try{
        disableForegroundDispatchSystem();
}
catch(Exception ex)
{
	System.out.println(ex);
	}
}
	//  NFc  Tag reading 
	
	@Override
	protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent);

        
    }
	
	private void parseIntent(Intent intent)
	{
		if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
	         //   Toast.makeText(this, "NfcIntent!", Toast.LENGTH_SHORT).show();
	          
	               Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

	                if(parcelables != null && parcelables.length > 0)
	                {
	                    readTextFromMessage((NdefMessage) parcelables[0]);
	                }else{
	                    Toast.makeText(this, "No NDEF messages found!", Toast.LENGTH_SHORT).show();
	                }
	        }
		
	}
	
	private void readTextFromMessage(NdefMessage ndefMessage) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        int monument_id;
   		String poiName;
        
        System.out.println(""+ndefRecords[0].getTnf());
        globalVariable=(GlobalClass) this.getApplicationContext();
       
              
        
        try{
   			SQLiteDatabase db  =this.openOrCreateDatabase("Tapnknow", 1, null);
   		String pid  = readText(ndefRecords[0]);
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
   	       					.replace(R.id.frame_container, new FindPeopleFragment()).commit();
   			}
   		}
   		catch(Exception ex){
   			
   			System.out.println(ex);       		
   			};
   		
   		
   		 
   	 }
      
	 
	

	 private void enableForegroundDispatchSystem() {

	        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

	        IntentFilter[] intentFilters = new IntentFilter[]{};
	        try{
	        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
	        }
	        catch(Exception ex)
	        {
	        	
	        	System.out.println(ex);
	        }
	    }

	    private void disableForegroundDispatchSystem() {
	        nfcAdapter.disableForegroundDispatch(this);
	    }


	   

	    

	      

	    private String readText(NdefRecord record) throws UnsupportedEncodingException {
	        /*
	         * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
	         * 
	         * http://www.nfc-forum.org/specs/
	         * 
	         * bit_7 defines encoding
	         * bit_6 reserved for future use, must be 0
	         * bit_5..0 length of IANA language code
	         */
	 
	        byte[] payload = record.getPayload();
	        System.out.println("payload of the tag is"+new String(payload));
	        System.out.println("payload lenght is "+payload.length);
	         
	        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
	        // e.g. "en"
	         
	        // Get the Text
	        String code =new String(payload); 
	        return code;
	    }
	    
	   
	    
	    private void SingleChoiceWithRadioButton() {  
			final CharSequence[] items = {"English", "Hindi"};
			 
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick a color");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		   }  
	    
	    
	   
	    
	    
		private void copyDatabase()
		{
			InputStream myInput;
			try {
				myInput = this.getAssets().open("Tapnknow");
		
		        // Path to the just created empty db
		        String outFileName =  getDatabasePath("Tapnknow").toString() ;
		        // Open the empty db as the output stream
		        OutputStream myOutput = new FileOutputStream(outFileName);
		        // transfer bytes from the inputfile to the outputfile
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = myInput.read(buffer)) > 0) {
		            myOutput.write(buffer, 0, length);
		        }
		        myOutput.flush();
		        myOutput.close();
		        myInput.close();
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   
 // Show Dialog Box with Progress bar
        @Override
        protected Dialog onCreateDialog(int id) {
            switch (id) {
            case progress_bar_type:
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("Please Wait App configuaration is  ongoing");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                prgDialog.setCancelable(false);
                prgDialog.show();
                return prgDialog;
            default:
                return null;
            }
        }
	    
	    class Databasetransfer extends AsyncTask<String, String, String> {
	    	 
            // Show Progress bar before downloading Music
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Shows Progress Bar Dialog and then call doInBackground method
                showDialog(progress_bar_type);
                
               

            
            }
 
            // Download Music File from Internet
            @Override
            protected String doInBackground(String... f_url) {
                int count;
               
                return null;
            }
 
            // While Downloading Music File
            protected void onProgressUpdate(String... progress) {
                // Set progress percentage
            	
                prgDialog.setProgress(Integer.parseInt(progress[0]));
            }
 
            
         
            @Override
            protected void onPostExecute(String file_url) {
               
            	
            	
          	  Handler handler = new Handler();
               
                
                SQLiteDatabase  db =getApplicationContext().openOrCreateDatabase("Tapnknow",1, null);
                
                String query ="select *from MonumentTable";
                 try
                 {
                Cursor cursor=db.rawQuery(query,null);
                dismissDialog(progress_bar_type);
                 }
                 catch(Exception  ex)
                 {
                	 copyDatabase();
                	 handler.postDelayed(new Runnable() {
                         public void run() {
                         	 dismissDialog(progress_bar_type);
                         }}, 4000);
                
                 }
                
                
            	               
               // Toast.makeText(getApplicationContext(), "Intial Settings completed", Toast.LENGTH_LONG).show();
               
               
            }
        }
 
}
