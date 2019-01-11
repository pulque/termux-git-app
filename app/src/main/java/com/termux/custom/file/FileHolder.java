package com.termux.custom.file;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.termux.BR;
import com.termux.R;
import com.termux.custom.base.BaseAdapter;
import com.termux.custom.bean.FilePath;
import com.termux.databinding.FileItemViewBinding;

import java.util.ArrayList;

/**
 * Created by LiZhe on 2018-10-31.
 * com.lizheblogs.databinding.ui.list
 */
public class FileHolder extends RecyclerView.ViewHolder {

    private final BaseAdapter<FilePath> adapter;
    private FileItemViewBinding mBinding;

    FileHolder(View v) {
        super(v);
        mBinding = FileItemViewBinding.bind(v);
        adapter = new BaseAdapter<>(R.layout.file_item, BR.file, new ArrayList<>());
    }

    public void setBinding(int variableId, FilePath object) {
        setBinding(variableId, object, null);
    }

    public void setBinding(int variableId, FilePath object, View.OnClickListener listener) {
        adapter.setListData(object.getFiles());
        adapter.setListener(listener);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.setVariable(variableId, object);
        mBinding.executePendingBindings();
    }
}

