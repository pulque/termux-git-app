package com.termux.custom.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LiZhe on 2018-10-31.
 * com.lizheblogs.databinding.ui.list
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    private List<T> listData;
    private View.OnClickListener listener;
    private int layoutId;
    private int variableId;

    public BaseAdapter(int layoutId, int variableId, List<T> listData) {
        this.listData = listData;
        this.variableId = variableId;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder(LayoutInflater.from(parent.getContext())
            .inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseHolder holder, int position) {
        T item = listData.get(position);
        holder.itemView.setOnClickListener(listener);
        holder.itemView.setTag(item);
        holder.setBinding(variableId, item);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}

