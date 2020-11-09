package com.example.englishdictionary.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.englishdictionary.DBHelper;
import com.example.englishdictionary.R;
import com.example.englishdictionary.adapter.ListAdapter;
import com.example.englishdictionary.adapter.ListShowAdapter;

import java.util.ArrayList;

public class ShowAllWordsFragment extends Fragment {
	private String title;
	private int page;

	ListView listView;
	ListShowAdapter listShowAdapter;

	ArrayList<String> ID_Array;
	ArrayList<String> EN_Array;
	ArrayList<String> HY_Array;
	ArrayList<String> PRON_Array;
	ArrayList<String> EXAMPLE_Array;
	SQLiteDatabase sqLiteDatabase;
	Cursor cursor;
	DBHelper dbHelper;

	TextView textView;
	public static ShowAllWordsFragment newInstance(int page, String title) {
		ShowAllWordsFragment fragmentFirst = new ShowAllWordsFragment();
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
//		Log.d("my", "oncreate");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.d("my", "oncreateView");

		View show = inflater.inflate(R.layout.fragment_home, container, false);
		textView = show.findViewById(R.id.textsShow);
		listView = show.findViewById(R.id.listsShow);
		ID_Array = new ArrayList<String>();
		EN_Array = new ArrayList<String>();
		HY_Array = new ArrayList<String>();
		PRON_Array = new ArrayList<String>();
		EXAMPLE_Array = new ArrayList<String>();
		dbHelper = new DBHelper(getContext());
		sqLiteDatabase = dbHelper.getWritableDatabase();
		cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + "", null);
		ID_Array.clear();
		EN_Array.clear();
		HY_Array.clear();
		PRON_Array.clear();
		EXAMPLE_Array.clear();
		if (cursor.moveToFirst()) {
			int x = 1;
			do {
				ID_Array.add(String.valueOf(x++));
				EN_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN)));
				HY_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HY)));
				PRON_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PRONOUNCE)));
				EXAMPLE_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EXAMPLE)));
			} while (cursor.moveToNext());
		}
		textView.setText("currently we have ( "+ EN_Array.size()+" word )");
		listShowAdapter = new ListShowAdapter(getContext(),
				ID_Array,
				EN_Array,
				HY_Array,
				PRON_Array,
				EXAMPLE_Array
		);
		listView.setAdapter(listShowAdapter);
		cursor.close();
		return show;
	}

}