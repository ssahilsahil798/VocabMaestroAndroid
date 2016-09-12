package com.master.vocab.vocabmaster.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.master.vocab.vocabmaster.Adapter.WordListAdapter;
import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.Models.WordStatus;
import com.master.vocab.vocabmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 12/09/16.
 */
public class WordActivity extends AppCompatActivity {

    private int cardCategoryStatus;
    private ArrayList<WordStatus> listWords;
    private ListView listView;
    private WordListAdapter wordAdapter;
    private int wordCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        Bundle bundle = getIntent().getExtras();
        cardCategoryStatus = bundle.getInt("card");

        listWords = new ArrayList<>();
        listView = (ListView)findViewById(R.id.list_words);
        wordAdapter = new WordListAdapter(WordActivity.this, listWords);
        listView.setAdapter(wordAdapter);
        fetchWordList();


    }

    private void fetchWordList() {
        ApiClient.getFeedApiInterface().getCategoryWordStatus(cardCategoryStatus,100, 0, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray objs = object.optJSONArray("objects");
                    for(int i=0;i<objs.length();i++){
                        JSONObject wordObj = objs.optJSONObject(i);
                        WordStatus word = new WordStatus();
                        word.word = wordObj.optJSONObject("word").optString("word");
                        word.card_status = wordObj.optString("card_status");
                        word.meaning = wordObj.optJSONObject("word").optString("meaning");
                        word.word_id = wordObj.optJSONObject("word").optInt("id");
                        word.word_number = wordCount;
                        word.word_status = wordObj.optString("word_status");
                        wordCount += 1;
                        listWords.add(word);
                        wordAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void failure(RetrofitError error) {


            }
        });
    }
}

