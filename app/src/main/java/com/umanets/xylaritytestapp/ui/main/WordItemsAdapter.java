package com.umanets.xylaritytestapp.ui.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umanets.xylaritytestapp.R;
import com.umanets.xylaritytestapp.data.model.WordItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

class WordItemsAdapter extends RecyclerView.Adapter<WordItemsAdapter.WordItemViewHolder> {

    private List<WordItem> mWordItems;

    @Inject
    public WordItemsAdapter() {
        mWordItems = new ArrayList<>();
    }

    public void setWordItems(List<WordItem> words) {
        mWordItems = words;
    }

    @Override
    public WordItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordItemViewHolder holder, int position) {
        WordItem word = mWordItems.get(position);
        holder.titleTextView.setText(word.name());
        holder.langTextView.setText(word.country());

    }

    @Override
    public int getItemCount() {
        return mWordItems.size();
    }

    class WordItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title) TextView titleTextView;
        @BindView(R.id.text_lange) TextView langTextView;

        public WordItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
