package com.master.vocab.vocabmaster.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.master.vocab.vocabmaster.Activity.WordActivity;
import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.Models.Cards;
import com.master.vocab.vocabmaster.Models.WordStatus;
import com.master.vocab.vocabmaster.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 13/09/16.
 */
public class WordListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private WordActivity context;
    ArrayList<WordStatus> listWords;


    public WordListAdapter(WordActivity wordActivity, ArrayList<WordStatus> listWords) {
        this.listWords = listWords;
        this.context = wordActivity;
        this.inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listWords.size();
    }

    @Override
    public WordStatus getItem(int i) {
        return listWords.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.word_list_row, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final WordStatus word = getItem(i);
        mViewHolder.wordNumb.setText("#" + word.word_number + "");
        mViewHolder.wordTitle.setText(word.word);
        mViewHolder.checkBox.setOnCheckedChangeListener(null);
        if(Integer.parseInt(word.word_status) == 1){
            mViewHolder.checkBox.setChecked(true);
        }else{
            mViewHolder.checkBox.setChecked(false);
        }

        mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(word.word_status) == 1){
                    ApiClient.getFeedApiInterface().changeWordStatus(word.word_id +"", 0 +"", new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {
                            try {
                                JSONObject obj = new JSONObject(s);
                                boolean updated = Boolean.parseBoolean(obj.optString("updated"));
                                if(updated == true)
                                {
                                    word.word_status = 0 + "";
                                    notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            word.word_status = 1 + "";
                            notifyDataSetChanged();
                        }
                    });
                }else{
                    ApiClient.getFeedApiInterface().changeWordStatus(word.word_id +"", 1 +"", new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {
                            try {
                                JSONObject obj = new JSONObject(s);
                                boolean updated = Boolean.parseBoolean(obj.optString("updated"));
                                if(updated == true)
                                {
                                    word.word_status = 1 + "";
                                    notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            word.word_status = 0 + "";
                            notifyDataSetChanged();
                        }
                    });
                }

            }
        });
        return convertView;
    }

    private class MyViewHolder {
        TextView wordNumb;
        TextView wordTitle;
        CheckBox checkBox;



        public MyViewHolder(View item) {
            wordNumb = (TextView) item.findViewById(R.id.word_number);
            wordTitle = (TextView) item.findViewById(R.id.word_title);
            checkBox = (CheckBox)item.findViewById(R.id.word_check_box);
        }
}
}
