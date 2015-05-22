package ari.ins.asi;



import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter { private Context mContext;
ArrayList<PointofInterestClass> list = new ArrayList<PointofInterestClass>();

// Constructor
public ImageAdapter(Context c, int resourceId, List<PointofInterestClass> employeeList) {
	mContext = c;
	this.list = list ;
}

@Override
public int getCount() {
	return list.size();

}

@Override
public Object getItem(int position) {
	return list.get(position);
}

@Override
public long getItemId(int position) {
	return 0;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	ImageView imageView = new ImageView(mContext);
	imageView.setImageBitmap(list.get(position).getBitmap());
	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	return imageView;
}

}