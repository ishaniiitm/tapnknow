package ari.ins.asi;

public class RowItem {
	  private int imageId;
	    private String title;
	    private String desc;
	     
	    public RowItem(int imageId,  String desc) {
	        this.imageId = imageId;
	        this.title = title;
	        this.desc = desc;
	    }
	    public int getImageId() {
	        return imageId;
	    }
	    public void setImageId(int imageId) {
	        this.imageId = imageId;
	    }
	    public String getDesc() {
	        return desc;
	    }
	    public void setDesc(String desc) {
	        this.desc = desc;
	    }
	  
	}


