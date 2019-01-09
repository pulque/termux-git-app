package com.termux.custom.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.android.databinding.library.baseAdapters.BR;
import com.termux.custom.Command;
import com.termux.databinding.DialogInputBinding ;
import com.termux.terminal.TerminalSession;

/**
 * Created by LiZhe on 2019-01-09.
 * com.termux.custom.utils
 */
public class DialogUtils {

    public static void showInputDialog(Activity activity, final TerminalSession session, final Command command) {
        DialogInputBinding inflate = DialogInputBinding.inflate(LayoutInflater.from(activity));
        inflate.setVariable(BR.command, command);
        inflate.executePendingBindings();
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(activity);
        inputDialog.setTitle("请输入参数").setView(inflate.getRoot());
        inputDialog.setPositiveButton("确定", (dialog, which) -> {
            command.setValue(inflate.valueET.getText().toString());
            session.write(command.getCommand().replace(command.getKey(), command.getValue()));
        });
        inputDialog.show();
    }
}
