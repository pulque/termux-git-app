package com.termux.custom;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.termux.databinding.CommandItemViewBinding ;

/**
 * Created by LiZhe on 2018-10-31.
 * com.lizheblogs.databinding.ui.list
 */
public class CommandHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;

    CommandHolder(View v) {
        super(v);
        mBinding = CommandItemViewBinding.bind(v);
    }

    public void setBinding(int variableId, Object object) {
        mBinding.setVariable(variableId, object);
        mBinding.executePendingBindings();
    }
}

