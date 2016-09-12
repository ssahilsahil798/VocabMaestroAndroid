package com.master.vocab.vocabmaster.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.master.vocab.vocabmaster.MainActivity;
import com.master.vocab.vocabmaster.Models.Cards;
import com.master.vocab.vocabmaster.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sahildeswal on 02/09/16.
 */
public class CardAdapter extends BaseAdapter{
    private final List<Cards> listItems;
    private LayoutInflater inflater;

    public CardAdapter(MainActivity mainActivity, List<Cards> listCards) {
        this.listItems = listCards;
        inflater = (LayoutInflater)mainActivity.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Cards getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Cards card = getItem(i);
        mViewHolder.tvTitle.setText("Card #" + card.CardCategory + "");
        mViewHolder.totalWords.setText(card.total_words + "");
        mViewHolder.wordsCompleted.setText(card.words_completed + "");
        mViewHolder.percentage.setText(((int)((float)card.words_completed/(float) card.total_words)*100) + "");


        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;
        TextView totalWords;
        TextView wordsCompleted;
        TextView percentage;

        ProgressBar progressBar;


        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.cardTitle);
            totalWords = (TextView) item.findViewById(R.id.words_total);
            wordsCompleted = (TextView) item.findViewById(R.id.words_completed);
            percentage = (TextView) item.findViewById(R.id.percentage_completed);
            progressBar = (ProgressBar) item.findViewById(R.id.progress_card);


        }
    }
}
