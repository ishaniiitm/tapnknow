package ari.ins.asi;

import android.graphics.Bitmap;



public class PointofInterestClass  {
	
	private Bitmap bmp;
	private String briefname ;
	

	public PointofInterestClass (Bitmap b, String n) {
		bmp = b;
		briefname = n;
		
	}

	public Bitmap getBitmap() {
		return bmp;
	}

	public String getBriefname () {
		return briefname;
	}

	

}
