package com.richard.record.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;
import com.richard.record.R;

public class RouteSelectAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private SparseArray<RouteOverLay> routeOverlays;
	
	private Context mContext;
	private int mSelection;

	public RouteSelectAdapter(Context context,SparseArray<RouteOverLay> routeOverlays){
		inflater=LayoutInflater.from(context);
		this.routeOverlays=routeOverlays;
		this.mContext=context;
	}
	@Override
	public int getCount() {
		return routeOverlays.size();
//		return 8;
	}
	@Override
	public Object getItem(int position) {
		return routeOverlays.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		ViewHolder holder;
		if(converView==null){
			holder=new ViewHolder();
			converView=inflater.inflate(R.layout.item_select_route, null);
			holder.tv_method_tip=(TextView) converView.findViewById(R.id.tv_method_tip);
			holder.tv_time=(TextView) converView.findViewById(R.id.tv_time);
			holder.tv_distance=(TextView) converView.findViewById(R.id.tv_distance);
			holder.v_top_line= converView.findViewById(R.id.v_top_line);
			converView.setTag(holder);
		}else{
			holder=(ViewHolder) converView.getTag();
		}
		//TODO

		
		int routeID = routeOverlays.keyAt(position);
        AMapNaviPath path  = routeOverlays.get(routeID).getAMapNaviPath();
       
        int distance = path.getAllLength();//单位为米，-1未查询到
        if(distance>=0){
        	 holder.tv_distance.setText((float)distance/1000+"公里");
        }else{
        	holder.tv_distance.setText("");
        }
        
        int time = path.getAllTime();//时间为秒，-1未查询到
        if(time>=0){
        	holder.tv_time.setText(time/3600+"小时"+(time-time/3600*3600)/60+"分钟");
        }else{
        	holder.tv_time.setText("");
        }
        
       
        String methodTxt="一";
        switch (position) {
		case 0:
			methodTxt="一";
			break;
		case 1:
			methodTxt="二";
			break;
		case 2:
			methodTxt="三";
			break;
		default:
			break;
		}
        holder.tv_method_tip.setText("方案"+methodTxt);
        
        
        if(position == mSelection){
        	holder.v_top_line.setBackgroundResource(R.color.blue);
        	holder.tv_method_tip.setTextColor(mContext.getResources().getColor(R.color.blue));
        	holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.blue));
        	holder.tv_distance.setTextColor(mContext.getResources().getColor(R.color.blue));
        }else{
        	holder.v_top_line.setBackgroundResource(R.color.white);
        	holder.tv_method_tip.setTextColor(mContext.getResources().getColor(R.color.middle_grey));
        	holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.middle_grey));
        	holder.tv_distance.setTextColor(mContext.getResources().getColor(R.color.middle_grey));
        }
       
		
		return converView;
	}

	private final class ViewHolder{
		private View v_top_line;
		private TextView tv_method_tip,tv_time,tv_distance;
		
	}
	public void setSelection(int selection){
		mSelection = selection;
	}

}
