package com.master.vocab.vocabmaster.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.Models.WordStatus;
import com.master.vocab.vocabmaster.R;
import com.master.vocab.vocabmaster.SharedPref.EventController;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 13/09/16.
 */
public class MeaningDialog extends DialogFragment {

    private WordStatus word;
    private TextView wordTextView;
    private TextView wordMeaning;
    private TextView wordExample;
    private LinearLayout unlearn;
    private LinearLayout learnt;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.getWindow().setLayout(width, height);

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setGravity(Gravity.CENTER);
        return dialog;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.meaning_dialog, container, false);
        word = (WordStatus) getArguments().getSerializable("word");
        wordTextView = (TextView)v.findViewById(R.id.word_detail);
        wordMeaning = (TextView)v.findViewById(R.id.word_meaning);
        wordExample = (TextView)v.findViewById(R.id.word_example);
        learnt = (LinearLayout)v.findViewById(R.id.learnt);
        unlearn = (LinearLayout)v.findViewById(R.id.unlearn);

        learnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getFeedApiInterface().changeWordStatus(word.word_id +"", 1 +"", new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            boolean updated = Boolean.parseBoolean(obj.optString("updated"));
                            if(updated == true)
                            {
                                word.word_status = 1 + "";
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("word_back", word);
                                EventController.getEventController().notify(EventController.EventHolder.Word_Change, bundle);
                                getDialog().dismiss();
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
        });

        unlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getFeedApiInterface().changeWordStatus(word.word_id +"", 0 +"", new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            boolean updated = Boolean.parseBoolean(obj.optString("updated"));
                            if(updated == true)
                            {
                                word.word_status = 0 + "";
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("word_back", word);
                                EventController.getEventController().notify(EventController.EventHolder.Word_Change, bundle);
                                getDialog().dismiss();
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
        });

        setData();
        return v;
    }

    private void setData() {
        wordTextView.setText(word.word);
        wordMeaning.setText(word.meaning);
        wordExample.setText("Example of using word will appear here. data entry part left.");

    }

}
