package com.termux.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.termux.R;
import com.termux.app.TermuxActivity;
import com.termux.terminal.TerminalSession;
import com.termux.view.TerminalView;

import java.io.File;

/**
 * Created by LiZhe on 2019-01-03.
 * com.termux.custom
 * <p>
 * <p>
 * apt update && apt upgrade
 * apt install git
 * termux-setup-storage
 * ln -s /data/data/com.termux/files/home/storage/shared/
 * github
 */
public class TermuxHelper implements View.OnClickListener {

    private String git_address = "https://github.com/pulque/pulque.github.io.git";
    private String user_name = "Norman";
    private String user_email = "pulqueli@gmail.com";
    private String user_git_name = "lizhe052@126.com";
    private String user_git_password = "";

    private TermuxActivity activity;

    private EditText editText = null;

    private android.widget.Button cdBut;
    private android.widget.Button uncdBut;
    private android.widget.Button lsBut;
    private android.widget.Button updateBut;
    private android.widget.Button gitInstallBut;
    private android.widget.Button storageBut;
    private android.widget.Button statusBut;
    private android.widget.Button addAllBut;
    private android.widget.Button commitBut;
    private android.widget.Button pushBut;
    private android.widget.Button cloneBut;
    private android.widget.Button pullBut;
    private android.widget.Button userEmailBut;
    private android.widget.Button userNameBut;
    private android.widget.Button userIDBut;
    private android.widget.Button userPWDBut;

    public TermuxHelper(TermuxActivity activity) {
        this.activity = activity;
    }

    public void init() {
        initUI();
        initClick();
    }

    private void initClick() {
        cdBut.setOnClickListener(this);
        uncdBut.setOnClickListener(this);
        lsBut.setOnClickListener(this);
        updateBut.setOnClickListener(this);
        gitInstallBut.setOnClickListener(this);
        storageBut.setOnClickListener(this);
        statusBut.setOnClickListener(this);
        addAllBut.setOnClickListener(this);
        commitBut.setOnClickListener(this);
        pushBut.setOnClickListener(this);
        cloneBut.setOnClickListener(this);
        pullBut.setOnClickListener(this);
        userEmailBut.setOnClickListener(this);
        userNameBut.setOnClickListener(this);
        userIDBut.setOnClickListener(this);
        userPWDBut.setOnClickListener(this);
    }

    private void initUI() {
        this.userPWDBut = (Button) activity.findViewById(R.id.userPWDBut);
        this.userIDBut = (Button) activity.findViewById(R.id.userIDBut);
        this.userNameBut = (Button) activity.findViewById(R.id.userNameBut);
        this.userEmailBut = (Button) activity.findViewById(R.id.userEmailBut);
        this.pullBut = (Button) activity.findViewById(R.id.pullBut);
        this.cloneBut = (Button) activity.findViewById(R.id.cloneBut);
        this.pushBut = (Button) activity.findViewById(R.id.pushBut);
        this.commitBut = (Button) activity.findViewById(R.id.commitBut);
        this.addAllBut = (Button) activity.findViewById(R.id.addAllBut);
        this.statusBut = (Button) activity.findViewById(R.id.statusBut);
        this.storageBut = (Button) activity.findViewById(R.id.storageBut);
        this.gitInstallBut = (Button) activity.findViewById(R.id.gitInstallBut);
        this.updateBut = (Button) activity.findViewById(R.id.updateBut);
        this.lsBut = (Button) activity.findViewById(R.id.lsBut);
        this.uncdBut = (Button) activity.findViewById(R.id.uncdBut);
        this.cdBut = (Button) activity.findViewById(R.id.cdBut);
    }

    @Override
    public void onClick(View v) {
        TerminalSession session = activity.getCurrentTermSession();
        if (session == null){
            return;
        }
        switch (v.getId()) {
            case R.id.cdBut:
                session.write("cd ");
                break;
            case R.id.uncdBut:
                session.write("cd ..\n");
                break;
            case R.id.lsBut:
                session.write("ls\n");
                break;
            case R.id.updateBut:
                session.write("apt update && apt upgrade\n");
                break;
            case R.id.gitInstallBut:
                session.write("apt install git\n");
                break;
            case R.id.storageBut:
                File storageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(storageDirectory.getAbsolutePath() + "/github");
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        session.write("创建文件夹失败！");
                        break;
                    }
                }
                session.write("termux-setup-storage\n");
                session.write("ln -s /data/data/com.termux/files/home/storage/shared/github\n");
                session.write("cd github\n");
                break;
            case R.id.statusBut:
                session.write("git status\n");
                break;
            case R.id.addAllBut:
                session.write("git add --all\n");
                break;
            case R.id.commitBut:
                editText = showInputDialog(activity, "输入注释", (dialog, which) -> {
                    String string = editText.getText().toString();
                    session.write("git commit -a -m \"" + string + "\"\n");
                });
                break;
            case R.id.pushBut:
                session.write("git push\n");
                break;
            case R.id.cloneBut:
                session.write("git clone " + git_address + "\n");
                break;
            case R.id.pullBut:
                session.write("git pull\n");
                break;
            case R.id.userEmailBut:
                session.write("git config user.email \"" + user_email + "\"\n");
                break;
            case R.id.userNameBut:
                session.write("git config user.name \"" + user_name + "\"\n");
                break;
            case R.id.userIDBut:
                session.write(user_git_name + "\n");
                break;
            case R.id.userPWDBut:
                session.write(user_git_password + "\n");
                break;

        }
    }

    private EditText showInputDialog(Activity activity, String title, DialogInterface.OnClickListener listener) {
        EditText editText = new EditText(activity);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(activity);
        inputDialog.setTitle(title).setView(editText);
        inputDialog.setPositiveButton("确定", listener).show();
        return editText;
    }
}
