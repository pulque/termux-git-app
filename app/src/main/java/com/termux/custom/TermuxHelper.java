package com.termux.custom;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.termux.R;
import com.termux.app.TermuxActivity;
import com.termux.custom.base.BaseAdapter;
import com.termux.custom.bean.Command;
import com.termux.custom.edit.EditActivity;
import com.termux.custom.utils.DialogUtils;
import com.termux.terminal.TerminalSession;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by LiZhe on 2019-01-03.
 * com.termux.custom
 * <p>
 *     使用列表的层级结构展示文件目录结构
 * <p>
 * apt update && apt upgrade
 * apt install git
 * termux-setup-storage
 * ln -s /data/data/com.termux/files/home/storage/shared/
 * github
 */
public class TermuxHelper implements View.OnClickListener {

    private TermuxActivity activity;

    public TermuxHelper(TermuxActivity activity) {
        this.activity = activity;
    }

    public void init() {
        RecyclerView commandList = activity.findViewById(R.id.commandList);
        int id = 0;
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command(++id, "编辑文件", ""));
        commands.add(new Command(++id, "y", "y\n"));
        commands.add(new Command(++id, "cd", "cd git_path\n", "git_path"));
        commands.add(new Command(++id, "uncd", "cd ..\n"));
        commands.add(new Command(++id, "ls", "ls\n"));
        commands.add(new Command(++id, "update apt", "apt update && apt upgrade\n"));
        commands.add(new Command(++id, "gitInstall", "apt install git\n"));
        commands.add(new Command(++id, "storage", session -> {
            File storageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(storageDirectory.getAbsolutePath() + "/github");
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    session.write("创建文件夹失败！");
                    return;
                }
            }
            session.write("termux-setup-storage\n");
            session.write("ln -s /data/data/com.termux/files/home/storage/shared/github\n");
            session.write("cd github\n");
        }));
        commands.add(new Command(++id, "git clone", "git clone git_address\n", "git_address"));
        commands.add(new Command(++id, "git.user.email", "git config user.email \"user_email\"\n",
            "user_email"));
        commands.add(new Command(++id, "git.user.name", "git config user.name \"user_name\"\n",
            "user_name"));
        commands.add(new Command(++id, "git pull", "git pull\n"));
        commands.add(new Command(++id, "git status", "git status\n"));
        commands.add(new Command(++id, "git add --all", "git add --all\n"));
        commands.add(new Command(++id, "git commit", "git commit -a -m \"annotation\"\n",
            "annotation", "Default annotation."));
        commands.add(new Command(++id, "git push", "git push\n"));
        commands.add(new Command(++id, "user_git_name", "user_git_name\n", "user_git_name"));
        commands.add(new Command(++id, "user_git_pwd", "user_git_pwd\n", "user_git_pwd"));

        BaseAdapter<Command> adapter = new BaseAdapter<>(R.layout.command_item_view, BR.command, commands);
        adapter.setListener(this);
        commandList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Command command = null;
        Object tag = v.getTag();
        if (tag instanceof Command) {
            command = (Command) tag;
        }
        if (command == null) {
            return;
        }
        if (command.getId() == 1) {
            activity.startActivity(new Intent(activity, EditActivity.class));
            return;
        }
        TerminalSession session = activity.getCurrentTermSession();
        if (session == null) {
            return;
        }
        if (!TextUtils.isEmpty(command.getKey())) {
            DialogUtils.showInputDialog(activity, session, command);
        } else if (command.getExecute() != null) {
            command.getExecute().execute(session);
        } else {
            session.write(command.getCommand());
        }
    }

}
