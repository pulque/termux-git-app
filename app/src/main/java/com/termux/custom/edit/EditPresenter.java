package com.termux.custom.edit;

/**
 * Created by LiZhe on 2018-12-25.
 * com.termux.custom.file
 */
public class EditPresenter {

    private EditFragment fragment;

    public EditPresenter(EditFragment fragment) {
        this.fragment = fragment;
    }


    public void onDestroy() {
        fragment = null;
    }
}
