package com.wolf.wolfsafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wolf.wolfsafe.db.ApplockDBOpenHelper;

/**
 * ��������dao
 * @author hanwen
 *
 */
public class ApplockDao {
	private ApplockDBOpenHelper helper;
	private Context context;
	/**
	 * ���췽��
	 * @param context ������
	 */
	public ApplockDao(Context context) {
		helper = new ApplockDBOpenHelper(context);
		this.context = context;
	}
	
	/**
	 * ����һ��Ҫ����Ӧ�ó���İ���
	 */
	public void add(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("packname", packname);
		db.insert("applock", null, values);
		db.close();
		
		Intent intent = new Intent();
		intent.setAction("com.wolf.wolfsafe.applockchange");
		context.sendBroadcast(intent);
//		context.getContentResolver().notifyChange(uri, observer)
	}
	/**
	 * ɾ��һ��Ҫ����Ӧ�ó���İ���
	 */
	public void delete(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("applock", "packname=?", new String[]{packname});
		db.close();
		Intent intent = new Intent();
		intent.setAction("com.wolf.wolfsafe.applockchange");
		context.sendBroadcast(intent);
	}
	
	/**
	 * ��ѯһ��������������¼�Ƿ����
	 * @param packname
	 * @return
	 */
	public boolean find(String packname) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("applock", null, "packname=?", new String[]{packname}, null, null, null);
		if(cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/**
	 * ��ѯȫ���İ���
	 * @param packname
	 * @return
	 */
	public List<String> findAll() {
		List<String> protectPackname = new ArrayList<String>();
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("applock", new String[]{"packname"}, null, null, null, null, null);
		while(cursor.moveToNext()) {
			protectPackname.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return protectPackname;
	}
	
}