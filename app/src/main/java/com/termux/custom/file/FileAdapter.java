package com.termux.custom.file;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.termux.custom.bean.FilePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiZhe on 2018-10-31.
 * com.lizheblogs.databinding.ui.list
 */
public class FileAdapter extends RecyclerView.Adapter<FileHolder> {
    private List<FilePath> listData;
    private GroupItemClickListener listener;
    private int layoutId;
    private int variableId;

    public FileAdapter(int layoutId, int variableId, List<FilePath> listData) {
        this.listData = listData;
        this.variableId = variableId;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, final int position) {
        final FilePath filePath = listData.get(position);
        if (listener != null) {
            holder.setBinding(variableId, filePath, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if (tag instanceof FilePath) {
                        ArrayList<FilePath> filePaths = filePath.getFiles();
                        int index = filePaths.indexOf(tag);
                        listener.onItemClick(position, index, 0);
                    }
                }
            });
        } else {
            holder.setBinding(variableId, filePath);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setListener(GroupItemClickListener listener) {
        this.listener = listener;
    }

}

