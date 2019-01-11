package com.termux.custom.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LiZhe on 2018-10-31.
 * com.lizheblogs.databinding.ui.list
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;

    BaseHolder(View v) {
        super(v);
        mBinding = DataBindingUtil.bind(v);
    }

    public void setBinding(int variableId, Object object) {
        mBinding.setVariable(variableId, object);
        mBinding.executePendingBindings();
    }
}

