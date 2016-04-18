package com.wolf.wolfsafe;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wolf.wolfsafe.db.dao.BlackNumberDao;
import com.wolf.wolfsafe.domain.BlackNumberInfo;

public class CallSmsSafeActivity extends Activity {
	
	private static final String TAG = "CallSmsSafeActivity";
	
	private ListView lv_callsms_safe;
	private List<BlackNumberInfo> infos;
	private BlackNumberDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
		
		dao = new BlackNumberDao(this);
		infos = dao.findAll();
		lv_callsms_safe.setAdapter(new CallSmsSafeAdapter());
	}
	
	
	private class CallSmsSafeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		} 

		
		 //有多少个条目被显示，这个方法就会被调用多少次
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			//1.减少内存中view对象创建的个数
			if(convertView == null) {
				Log.i(TAG,"创建新的view对象" + position);
				//把一个布局文件转换成view对象
			    view = View.inflate(getApplicationContext(), R.layout.list_item_callsms, null);
			    
			  //2.减少孩子查询的次数//内存中对象的地址
				holder = new ViewHolder();
				holder.tv_number = (TextView) view.findViewById(R.id.tv_black_number);
				holder.tv_mode = (TextView) view.findViewById(R.id.tv_block_mode);
				//当孩子生出来的时候找到他们的引用，存放在记事本，放在父亲的口袋
				view.setTag(holder);
			}else {
				Log.i(TAG,"以前的view对象，复用历史缓存的view对象" + position);
				view = convertView;
				holder = (ViewHolder) view.getTag(); //5%
			}
			holder.tv_number.setText(infos.get(position).getNumber());
			String mode = infos.get(position).getMode();
			
			if("1".equals(mode)) {
				holder.tv_mode.setText("电话拦截");
			}else if("2".equals(mode)) {
				holder.tv_mode.setText("短信拦截");
			}else {
				holder.tv_mode.setText("全部拦截");
			}
			
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	/**
	 * view 对象的容器
	 * 记录孩子的内存地址
	 * 相当于一个记事本
	 * @author Hanwen
	 *
	 */
	static class ViewHolder {
		TextView tv_number;
		TextView tv_mode;
	}
}
