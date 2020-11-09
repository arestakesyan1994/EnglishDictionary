package com.example.englishdictionary.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.englishdictionary.DBHelper;
import com.example.englishdictionary.adapter.ListAdapter;
import com.example.englishdictionary.R;

import java.util.ArrayList;

public class AllFragment extends Fragment {

	private String title;
	private int page;

	ListView listView;
	ListAdapter listAdapter;

	ArrayList<String> EN_Array;
	ArrayList<String> HY_Array;
	ArrayList<String> EXAMPLE_Array;
	ArrayList<String> EN_VERSION_Array;
	SQLiteDatabase sqLiteDatabase;
	Cursor cursor;
	DBHelper dbHelper;

	TextView textView;
	public static AllFragment newInstance(int page, String title) {
		AllFragment fragmentFirst = new AllFragment();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		page = getArguments().getInt("someInt", 0);
//		title = getArguments().getString("someTitle");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all, container, false);
		textView = view.findViewById(R.id.texts);
		listView = view.findViewById(R.id.lists);
		EN_Array = new ArrayList<String>();
		HY_Array = new ArrayList<String>();
		EXAMPLE_Array = new ArrayList<String>();
		EN_VERSION_Array = new ArrayList<String>();
		dbHelper = new DBHelper(getContext());
		sqLiteDatabase = dbHelper.getWritableDatabase();
		cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + "", null);
		EN_Array.clear();
		HY_Array.clear();
		EXAMPLE_Array.clear();
		EN_VERSION_Array.clear();
		if (cursor.moveToFirst()) {
			do {
				EN_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN)));
				HY_Array.add("( "+cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HY))+" )");
				EXAMPLE_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EXAMPLE)));
				EN_VERSION_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN_VERSION)));
			} while (cursor.moveToNext());
		}
		textView.setText("currently we have ( "+ EN_Array.size()+" word )");
		listAdapter = new ListAdapter(getContext(),
				EN_Array,
				HY_Array,
				EXAMPLE_Array,
				EN_VERSION_Array
		);
		listView.setAdapter(listAdapter);
		cursor.close();

		return view;
	}
}