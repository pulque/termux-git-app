package com.termux.custom.file;

import com.termux.custom.edit.EditFragment;

/**
 * Created by LiZhe on 2018-12-25.
 * com.termux.custom.file
 */
public class FilePresenter {

    private EditFragment fragment;

    public FilePresenter(EditFragment fragment) {
        this.fragment = fragment;
    }


    public void onDestroy() {
        fragment = null;
    }
}
