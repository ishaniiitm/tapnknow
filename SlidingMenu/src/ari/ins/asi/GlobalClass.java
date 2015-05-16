package ari.ins.asi;

import android.app.Application;


public class GlobalClass  extends Application {
	
	private   int  id;
	private   int  monument_ID;
	
	 public String  name;

	public String language ="en";
    
	 
    public Integer getid()  {
         
        return id;
    }
	
    public void setID(int  cid) {
        
        id = cid;
          
     }

    public void setLanguage(String lang) {
        
	language = lang;
          
     }
    
   public String  getLanguage()  {
        
        return language;
    }
     
    
    
    
 public void setMonument_ID(int  cid) {
        
        monument_ID = cid;
          
     }
 public Integer getMonument_ID()  {
     
     return monument_ID;
 }
    
    public String  getName()  {
        
        return name;
    }
	
    public void setName(String  name2) {
    	
    	 name=name2;
        
       
          
     }
    
    
	
	

}
