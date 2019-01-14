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
import android.widget.Toast;

import com.termux.R;
import com.termux.custom.file.FileActivity;
import com.termux.custom.file.FileFragment;
import com.termux.custom.utils.LocalDataRepository;
import com.termux.custom.utils.StringUtils;
import com.termux.databinding.EditFragmentBinding;

import java.io.File;


/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.edit
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    public static final int FOR_PATH_RESULT = 1000;
    private final String FUNCTION_EDIT_PATH = "FUNCTION_EDIT_PATH";

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
        String cachePath = getCachePath();
        initPath(cachePath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectBut:
                Intent intent = new Intent(getContext(), FileActivity.class);
                intent.putExtra(FileActivity.TAG_PATH_FILE, binding.addressET.getText().toString());
                startActivityForResult(intent, FOR_PATH_RESULT);
                break;
            case R.id.saveBut:
                String address = binding.addressET.getText().toString();
                File file = new File(address);
                if (!file.exists() || !file.isFile()) {
                    Toast.makeText(getContext(), "Path is error!", Toast.LENGTH_LONG).show();
                } else {
                    String string = binding.textET.getText().toString();
                    StringUtils.writeStringToFile(string, file.getParentFile().getAbsolutePath(), file.getName());
                    Toast.makeText(getContext(), "DONE!", Toast.LENGTH_SHORT).show();
                    savePath(address);
                }
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
                initPath(string);
                savePath(string);
                break;
        }
    }

    private void initPath(String string) {
        binding.addressET.setText(string);
        if (!TextUtils.isEmpty(string)) {
            File file = new File(string);
            if (file.exists() && file.isFile()) {
                binding.textET.setText(StringUtils.readToString(file));
            }else {
                binding.textET.setText("");
            }
        }
    }

    private void savePath(String path) {
        LocalDataRepository.getInstance().setString(FUNCTION_EDIT_PATH, path);
    }

    private String getCachePath() {
        return LocalDataRepository.getInstance().getString(FUNCTION_EDIT_PATH);
    }

}
