package com.termux.custom.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.termux.R;
import com.termux.custom.base.BaseAdapter;
import com.termux.custom.bean.Command;
import com.termux.custom.file.FileActivity;
import com.termux.custom.file.FileFragment;
import com.termux.custom.utils.LocalDataRepository;
import com.termux.custom.utils.StringUtils;
import com.termux.databinding.EditFragmentBinding;

import java.io.File;
import java.util.ArrayList;


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
        String cachePath = getCachePath();
        initPath(cachePath);

        int id = 0;
        ArrayList<Command> commands = new ArrayList<>();
        ;
        commands.add(new Command(++id, "SAVE", ""));
        commands.add(new Command(++id, "SELECT", ""));
        commands.add(new Command(++id, "init page", "---\n" +
            "layout: default\n" +
            "title: \"\"\n" +
            "---\n" +
            "\n" +
            "<h2>{{ page.title }}</h2>\n" +
            "\n" +
            "<p class=\"publish_date\">31 Jan 2019</p>"));
        commands.add(new Command(++id, "BR", "<br/>"));
        commands.add(new Command(++id, "P", "<P></P>"));
        commands.add(new Command(++id, "h3", "<h3></h3>"));
        commands.add(new Command(++id, "h4", "<h4></h4>"));
        commands.add(new Command(++id, "strong", "<strong></strong>"));
        commands.add(new Command(++id, "pre", "<pre></pre>"));
        commands.add(new Command(++id, "blockquote", "<blockquote></blockquote>"));
        commands.add(new Command(++id, "font", "<font class=\"green\"></font>"));
        commands.add(new Command(++id, "<>", "&lt;&gt;"));
        commands.add(new Command(++id, "link", "<a target=\"_blank\" href=\"https://\">\n" +
            "            https://</a><br/> "));
        commands.add(new Command(++id, "image", "<div>\n" +
            "  <img src=\"/images/blogs/idea/file_name/image.png\" width=\"512\" alt=\"\"/>\n" +
            "  </div>"));

        commands.add(new Command(++id, "audio", "<audio src=\"/audio/blogs/idea/file_name/20181104_001.m4a\"" +
            " controls=\"controls\">\n" +
            "Your browser does not support the audio element.\n" +
            "</audio>"));

        commands.add(new Command(++id, "video", "<video src=\"/video/blogs/idea/file_name/20181104_001.m4a\"" +
            " controls=\"controls\">\n" +
            "Your browser does not support the video element.\n" +
            "</video>"));

        BaseAdapter<Command> adapter = new BaseAdapter<>(R.layout.edit_item_view, BR.command, commands);
        adapter.setListener(this);
        binding.commandList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Command command = null;
        Object tag = v.getTag();
        if (tag instanceof Command) {
            command = (Command) tag;
        }
        if (command != null) {
            switch (command.getId()) {
                case 1://save
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
                case 2://select
                    Intent intent = new Intent(getContext(), FileActivity.class);
                    intent.putExtra(FileActivity.TAG_PATH_FILE, binding.addressET.getText().toString());
                    startActivityForResult(intent, FOR_PATH_RESULT);
                    break;
                default:
                    EditText mEditText = binding.textET;//EditText对象
                    int index = mEditText.getSelectionStart();//获取光标所在位置
                    Editable edit = mEditText.getEditableText();//获取EditText的文字
                    if (index < 0 || index >= edit.length()) {
                        edit.append(command.getCommand());
                    } else {
                        edit.insert(index, command.getCommand());//光标所在位置插入文字
                    }
                    int position = command.getCommand().indexOf("><");
                    if (position != -1) {
                        mEditText.setSelection(index + position + 1);
                    }
                    break;
            }

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
            } else {
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
