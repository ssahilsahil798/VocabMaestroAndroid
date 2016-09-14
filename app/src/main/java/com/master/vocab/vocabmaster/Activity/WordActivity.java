package com.master.vocab.vocabmaster.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.master.vocab.vocabmaster.Adapter.WordListAdapter;
import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.Fragments.MeaningDialog;
import com.master.vocab.vocabmaster.Models.WordStatus;
import com.master.vocab.vocabmaster.R;
import com.master.vocab.vocabmaster.SharedPref.EventController;
import com.master.vocab.vocabmaster.SharedPref.EventListeners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 12/09/16.
 */
public class WordActivity extends AppCompatActivity implements EventListeners {

    private int cardCategoryStatus;
    private ArrayList<WordStatus> listWords;
    private ListView listView;
    private WordListAdapter wordAdapter;
    private int wordCount = 1;
    private ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Words");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        fetchWordList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("check", "click working");
                MeaningDialog dialogMeaning = new MeaningDialog();
                Bundle args = new Bundle();
                args.putSerializable("word", listWords.get(i));
                dialogMeaning.setArguments(args);
                dialogMeaning.show(getSupportFragmentManager(), "meaning");
            }
        });


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        EventController.getEventController().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventController.getEventController().unregister(this);
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
                        word.word_id = wordObj.optInt("id");
                        word.word_number = wordCount;
                        word.word_status = wordObj.optString("word_status");
                        wordCount += 1;
                        listWords.add(word);
                        wordAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                progressDialog.hide();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.hide();

            }
        });
    }

    @Override
    public boolean handleEvent(int event, Bundle bundle) {
        switch (event){
            case EventController.EventHolder.Word_Change:
                WordStatus word_back = (WordStatus) bundle.getSerializable("word_back");
                for(int i=0; i<listWords.size();i++){
                    if(listWords.get(i).word_number == word_back.word_number){
                        listWords.remove(listWords.get(i));
                        listWords.add(i, word_back);
                        wordAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
        return false;
    }
}

