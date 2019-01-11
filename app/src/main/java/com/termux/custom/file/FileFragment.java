package com.termux.custom.file;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.termux.BR;
import com.termux.R;
import com.termux.custom.bean.FilePath;
import com.termux.databinding.FileFragmentBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.edit
 */
public class FileFragment extends Fragment implements View.OnClickListener {

    public final int PATH_RESULT = 1001;
    public static final String PATH_SELECTED = "PATH_SELECTED";

    private FileFragmentBinding binding;
    private ArrayList<FilePath> filePaths;
    private FileAdapter fileAdapter;

    public static FileFragment newInstance() {
        return new FileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        filePaths = new ArrayList<>();
        binding = FileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.upBut.setOnClickListener(this);
        binding.downBut.setOnClickListener(this);
        binding.doneBut.setOnClickListener(this);

        File root = Environment.getExternalStorageDirectory();
        addNewFiles(root);
        fileAdapter = new FileAdapter(R.layout.file_item_view, BR.file, filePaths);
        fileAdapter.setListener((positionOfGroup, positionOfItem, tag) -> {
            if (positionOfGroup + 1 < filePaths.size()) {
                List<FilePath> tempPath = this.filePaths.subList(positionOfGroup + 1, filePaths.size());
                filePaths.removeAll(tempPath);
            }
            FilePath filePath = filePaths.get(positionOfGroup);
            ArrayList<FilePath> files = unSelectAll(filePath);
            FilePath child = files.get(positionOfItem);
            child.setSelect(true);
            if (child.file.isDirectory()) {
                addNewFiles(child.file);
            }
            notifyDataSetChanged();
        });

        binding.recyclerView.setAdapter(fileAdapter);

    }

    @NonNull
    private ArrayList<FilePath> unSelectAll(FilePath filePath) {
        ArrayList<FilePath> files = filePath.getFiles();
        for (FilePath file : files) {
            file.isSelect = false;
        }
        return files;
    }

    private void addNewFiles(File root) {
        FilePath rootPath = new FilePath(root);
        if (root.isDirectory()) {
            ArrayList<FilePath> filePathsChildren = new ArrayList<>();
            File[] files = root.listFiles();
            for (File file : files) {
                filePathsChildren.add(new FilePath(file));
            }
            rootPath.files = filePathsChildren;
        }
        filePaths.add(rootPath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upBut:
                if (filePaths.size() > 1) {
                    filePaths.remove(filePaths.size() - 1);
                    FilePath filePath = filePaths.get(filePaths.size() - 1);
                    unSelectAll(filePath);
                    notifyDataSetChanged();
                }
                break;
            case R.id.downBut:
                FilePath filePath = filePaths.get(filePaths.size() - 1);
                ArrayList<FilePath> files = filePath.files;
                for (FilePath file : files) {
                    if (file.file.isDirectory()) {
                        addNewFiles(file.file);
                        file.isSelect = true;
                        notifyDataSetChanged();
                        break;
                    }
                }
                break;
            case R.id.doneBut:
                Intent intent = new Intent();
                intent.putExtra(PATH_SELECTED, binding.addressET.getText().toString());
                FragmentActivity activity = getActivity();
                if (activity != null && !activity.isFinishing()) {
                    activity.setResult(PATH_RESULT, intent);
                    activity.finish();
                }
                break;
        }
    }

    private void notifyDataSetChanged() {
        int size = filePaths.size();
        if (size > 0) {
            binding.recyclerView.smoothScrollToPosition(size - 1);
            FilePath filePath = filePaths.get(size - 1);
            binding.addressET.setText(filePath.file.getAbsolutePath());
            if (filePath.file.isDirectory()) {
                ArrayList<FilePath> files = filePath.files;
                if (files != null) {
                    for (FilePath file : files) {
                        if (file.isSelect) {
                            binding.addressET.setText(file.file.getAbsolutePath());
                        }
                    }
                }
            }
        }
        fileAdapter.notifyDataSetChanged();

    }
}
