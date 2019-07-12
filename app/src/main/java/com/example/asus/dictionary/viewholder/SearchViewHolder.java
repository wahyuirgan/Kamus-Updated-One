package com.example.asus.dictionary.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.dictionary.DetailDictionaryActivity;
import com.example.asus.dictionary.R;
import com.example.asus.dictionary.model.DictionaryModel;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private TextView tvVocabulary, tvMeaning;

    public SearchViewHolder(View itemView) {
        super(itemView);

        tvVocabulary  = itemView.findViewById(R.id.tvVocabulary);
        tvMeaning      = itemView.findViewById(R.id.tvMeaning);
    }

    public void bind(final DictionaryModel dictionaryModel) {
        tvVocabulary.setText(dictionaryModel.getWord());
        tvMeaning.setText(dictionaryModel.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailDictionaryActivity.class);
                intent.putExtra(DetailDictionaryActivity.ITEM_VOCABULARY, dictionaryModel.getWord());
                intent.putExtra(DetailDictionaryActivity.ITEM_MEANING, dictionaryModel.getDescription());
                intent.putExtra(DetailDictionaryActivity.ITEM_CATEGORY, dictionaryModel.getCategory());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
