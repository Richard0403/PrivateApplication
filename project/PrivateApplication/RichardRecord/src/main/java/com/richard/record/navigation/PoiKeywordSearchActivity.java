package com.richard.record.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.richard.record.R;
import com.richard.record.base.BaseActivity;
import com.richard.record.constants.Config;
import com.richard.record.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AMapV1地图中简单介绍poisearch搜索
 */
public class PoiKeywordSearchActivity extends BaseActivity implements
		 TextWatcher,
		OnPoiSearchListener, OnClickListener, InputtipsListener{

//	private AutoCompleteTextView searchText;// 输入搜索关键字
	private String keyWord = "";// 要输入的poi搜索关键字
	private ProgressDialog progDialog = null;// 搜索时进度条
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索
	
	
	
	//文字提示
	private ImageView iv_back;
	private EditText et_search;
	private ListView lv_text_tip;
	private Button searchButton;
	private TextTipAdapter textTipAdapter;
	private List<Tip> txtTipList = new ArrayList<Tip>();
	private List<Tip> historyTipList;
	private boolean isChange = false;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poikeywordsearch);
		initView();
		initData();
	}

	protected void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		et_search = (EditText) findViewById(R.id.et_search);
		iv_back.setOnClickListener(this);
		et_search.addTextChangedListener(this);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);
		
		
		lv_text_tip = (ListView) findViewById(R.id.lv_text_tip);
		textTipAdapter = new TextTipAdapter(this, txtTipList);
		lv_text_tip.setAdapter(textTipAdapter);
		lv_text_tip.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Tip tip = (Tip) txtTipList.get(position);
				hideInputAndList();
				putTipHistory(tip);
				//TODO
//				Fragment_Vedio.poiItems.clear();
//				PoiItem poi = new PoiItem(tip.getPoiID(), tip.getPoint(), tip.getName(), tip.getAddress());
//				Fragment_Vedio.poiItems.add(poi);
//				setResult(Fragment_Vedio.reqPoiSearchCode);
//				finish();
			}

			
		});
		historyTipList = Config.getConfig(this).getPOIHistoryList(Tip[].class);
		txtTipList.addAll(historyTipList);
		textTipAdapter.notifyDataSetChanged();
	}

	@Override
	protected void initData() {

	}


	private void putTipHistory(Tip tip) {
		//TODO
		if(isChange){
			historyTipList.add(tip);
			Collections.reverse(historyTipList);
			
			if(historyTipList.size()>10){
				historyTipList.remove(historyTipList.size()-1);
			}
			Config.getConfig(this).setPOIHistoryList(historyTipList);
		}
	}

   

	

	/**
	 * 点击搜索按钮
	 */
	public void searchButton() {
		hideInputAndList();
		keyWord = AMapUtil.checkEditText(et_search);
		if ("".equals(keyWord)) {
			ToastUtil.show(PoiKeywordSearchActivity.this, "请输入搜索关键字");
			return;
		} else {
			doSearchQuery();
		}
	}
	
	private void hideInputAndList(){
		txtTipList.clear();
		textTipAdapter.notifyDataSetChanged();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0); //强制隐藏键盘  

	}

	

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索:\n" + keyWord);
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		showProgressDialog();// 显示进度框
		currentPage = 0;
		query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页

		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}





	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName();
		}
		ToastUtil.show(PoiKeywordSearchActivity.this, infomation);

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String newText = s.toString().trim();
		isChange = true;;
		if (!AMapUtil.IsEmptyOrNullString(newText)) {
			//TODO
		    InputtipsQuery inputquery = new InputtipsQuery(newText, "");
		    Inputtips inputTips = new Inputtips(PoiKeywordSearchActivity.this, inputquery);
		    inputTips.setInputtipsListener(this);
		    inputTips.requestInputtipsAsyn();
		}else{
			txtTipList.clear();
			textTipAdapter.notifyDataSetChanged();
		}
	}


	/**

	 * POI信息查询回调方法
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		dissmissProgressDialog();// 隐藏对话框
		if (rCode == 1000) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

					if (poiItems != null && poiItems.size() > 0) {
//						Fragment_Vedio.poiItems.clear();
//						Fragment_Vedio.poiItems.addAll(poiItems);
//						setResult(Fragment_Vedio.reqPoiSearchCode);
//						finish();
//						aMap.clear();// 清理之前的图标
//						if(poiOverlay!=null){
//							poiOverlay.removeFromMap();
//						}
//						poiOverlay = new PoiOverlay(aMap, poiItems);
//						poiOverlay.removeFromMap();
//						poiOverlay.addToMap();
//						poiOverlay.zoomToSpan();
					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						ToastUtil.show(PoiKeywordSearchActivity.this,
								R.string.no_result);
					}
				}
			} else {
				ToastUtil.show(PoiKeywordSearchActivity.this,
						R.string.no_result);
			}
		} else {
//			ToastUtil.showerror(this, rCode);
		}

	}
	
	@Override
	public void onPoiItemSearched(PoiItem item, int rCode) {
		
	}

	/**
	 * Button点击事件回调方法
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 点击搜索按钮
		 */
		case R.id.searchButton:
			searchButton();
			break;
		case R.id.iv_back:
			finish();
			break;



		default:
			break;
		}
	}



	@Override
	public void onGetInputtips(List<Tip> tipList, int rCode) {
		if (rCode == 1000) {// 正确返回
			//TODO
			txtTipList.clear();
			for (int i = 0; i < tipList.size(); i++) {
				Tip tip = tipList.get(i);
				txtTipList.add(tip);
			}
			if(et_search.getText()!=null&&et_search.getText().toString()!="")
				textTipAdapter.notifyDataSetChanged();
		} else {
//			ToastUtil.showerror(this, rCode);
		}
		
	}


	

}
