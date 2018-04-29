package com.richard.record.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.richard.record.R;

import java.util.List;

public class TextTipAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Tip> listData;
	
	private Context mContext;

	public TextTipAdapter(Context context,List<Tip> listData){
		inflater=LayoutInflater.from(context);
		this.listData=listData;
		this.mContext=context;
	}
	@Override
	public int getCount() {
		return listData.size();
//		return 8;
	}
	@Override
	public Object getItem(int position) {
		return listData.get(position);
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
			converView=inflater.inflate(R.layout.item_text_tip, null);
			holder.tv_desc=(TextView) converView.findViewById(R.id.tv_desc);
			converView.setTag(holder);
		}else{
			holder=(ViewHolder) converView.getTag();
		}
		//TODO
		Tip tip = listData.get(position);
		if(tip!=null){
			holder.tv_desc.setText(tip.getName());
		}
		
		
		
		return converView;
	}

	private final class ViewHolder{
		private TextView tv_desc;
		private ImageView iv_pic;
		
	}

}
