package com.example.englishdictionary;

import android.content.Intent;
import android.os.Bundle;

import com.example.englishdictionary.fragment.AllFragment;
import com.example.englishdictionary.fragment.ShowAllWordsFragment;
import com.example.englishdictionary.fragment.SwitchFragment;
import com.example.englishdictionary.fragment.QuestionsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabItem t1, t2, t3;
    FragmentPagerAdapter adapter;
    static String[] strings = {"All","Show all words", "Switch", "Questions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        t1 = findViewById(R.id.tabItem1);
        t2 = findViewById(R.id.tabItem2);
        t3 = findViewById(R.id.tabItem3);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewWord.class);
                startActivity(intent);
            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS =4 ;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AllFragment.newInstance(0, "Page # 1");
                case 1:
                    return ShowAllWordsFragment.newInstance(1, "Page # 2");
                case 2:
                    return SwitchFragment.newInstance(2, "Page # 3");
                case 3:
                    return QuestionsFragment.newInstance(2, "Page # 4");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}