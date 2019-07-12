package com.example.asus.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.asus.dictionary.model.DictionaryModel;
import com.example.asus.dictionary.R;
import com.example.asus.dictionary.viewholder.SearchViewHolder;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private ArrayList<DictionaryModel> list = new ArrayList<>();

    public SearchAdapter() {
    }

    public void replaceAll(ArrayList<DictionaryModel> items) {
        list = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
