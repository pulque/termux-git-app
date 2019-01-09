package com.termux.custom;

import android.text.TextUtils;

import com.termux.custom.utils.ExecuteInterface;

/**
 * Created by LiZhe on 2019-01-08.
 * com.termux.custom
 * 一条命令允许有一个自定义参数
 * key为替换值
 * 也是本地保存的key
 */
public class Command {

    public Command(int id, String title, String command, String key, String value) {
        this.id = id;
        this.title = title;
        this.command = command;
        this.key = key;
        this.value = value;
    }

    public Command(int id, String title, String command, String key) {
        this.id = id;
        this.title = title;
        this.command = command;
        this.key = key;
    }

    public Command(int id, String title, String command) {
        this.id = id;
        this.title = title;
        this.command = command;
    }

    public Command(int id, String title, ExecuteInterface execute) {
        this.id = id;
        this.title = title;
        this.execute = execute;
    }

    private int id;
    //名称，用于按钮显示
    private String title;
    //实际命令
    private String command;
    //命令中的可以替换的标识
    private String key;
    //命令中的可以替换的值
    private String value;
    //自定义操作
    private ExecuteInterface execute;

    public String getValue() {
        if (TextUtils.isEmpty(value)) {
            value = LocalDataRepository.getInstance().getString(key);
        }
        return value;
    }

    public void setValue(String value) {
        LocalDataRepository.getInstance().setString(key, value);
        this.value = value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ExecuteInterface getExecute() {
        return execute;
    }

    public void setExecute(ExecuteInterface execute) {
        this.execute = execute;
    }
}
