package com.richard.record.widget;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;


public class MaxListView extends ListView{
	public MaxListView(Context context) {
		super(context);
	}

	public MaxListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MaxListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
}
