package com.termux.custom.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.termux.R;
import com.termux.custom.file.FileActivity;
import com.termux.custom.file.FileFragment;
import com.termux.custom.utils.StringUtils;
import com.termux.databinding.EditFragmentBinding;

import java.io.File;


/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.edit
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    public final int FOR_PATH_RESULT = 1000;

    private EditFragmentBinding binding;

    public static EditFragment newInstance() {
        return new EditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EditFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.selectBut.setOnClickListener(this);
        binding.saveBut.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectBut:
                startActivityForResult(new Intent(getContext(), FileActivity.class), FOR_PATH_RESULT);
                break;
            case R.id.saveBut:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case FOR_PATH_RESULT:
                String string = data.getStringExtra(FileFragment.PATH_SELECTED);
                binding.addressET.setText(string);
                if (!TextUtils.isEmpty(string)){
                    File file = new File(string);
                    if (file.isFile()){
                        binding.textET.setText(StringUtils.readToString(file));
                    }
                }
                break;
        }
    }
}
