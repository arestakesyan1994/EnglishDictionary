package com.example.englishdictionary.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishdictionary.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListAdapter extends BaseAdapter implements SectionIndexer {
    Context context;
    ArrayList<String> EN;
    ArrayList<String> HY;
    ArrayList<String> EXAMPLE;
    ArrayList<String> EN_VERSION;

    TextToSpeech mTTS, mTTS_example;

    public ListAdapter(Context context, ArrayList<String> EN, ArrayList<String> HY, ArrayList<String> EXAMPLE, ArrayList<String> EN_VERSION) {
        this.context = context;
        this.EN = EN;
        this.HY = HY;
        this.EXAMPLE = EXAMPLE;
        this.EN_VERSION = EN_VERSION;
    }

    public int getCount() {
        return EN.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View child, ViewGroup parent) {
        final Word word;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.items, null);
            word = new Word();
            word.en_TextView = (TextView) child.findViewById(R.id.textEn);
            word.hy_TextView = (TextView) child.findViewById(R.id.textHy);
            word.example_TextView = (TextView) child.findViewById(R.id.textEx);
            word.enVersion_TextView = (TextView) child.findViewById(R.id.enVersion);
            word.speakEn = (ImageButton) child.findViewById(R.id.speakEn);
            word.speakExample = (ImageButton) child.findViewById(R.id.speakExample);

            mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = mTTS.setLanguage(Locale.ENGLISH);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("my", "language not supported");
                        } else {
                            word.speakEn.setEnabled(true);
                        }
                    } else {
                        Log.e("my", "Initialization failed");
                    }
                }
            });

            mTTS_example = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = mTTS_example.setLanguage(Locale.ENGLISH);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("my", "language not supported");
                        } else {
                            word.speakExample.setEnabled(true);
                        }
                    } else {
                        Log.e("my", "Initialization failed");
                    }
                }
            });

            word.speakEn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTTS.setPitch(1f);
                    mTTS.setSpeechRate(1f);
                    mTTS.speak(EN.get(position), TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                }
            });

            word.speakExample.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTTS_example.setPitch(1f);
                    mTTS_example.setSpeechRate(1f);
                    mTTS_example.speak(EXAMPLE.get(position), TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            child.setTag(word);
        } else {

            word = (Word) child.getTag();
        }
        word.en_TextView.setText(EN.get(position));
        word.hy_TextView.setText(HY.get(position));
        word.example_TextView.setText(EXAMPLE.get(position));
        word.enVersion_TextView.setText(EN_VERSION.get(position));
        return child;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int i) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    public class Word {
        TextView en_TextView;
        TextView hy_TextView;
        TextView example_TextView;
        TextView enVersion_TextView;
        ImageButton speakEn;
        ImageButton speakExample;
    }
}
