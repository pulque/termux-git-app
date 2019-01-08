package com.termux.custom;

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

    private int id;
    //名称，用于按钮显示
    private String title;
    //实际命令
    private String command;
    //命令中的可以替换的标识
    private String key;
    //命令中的可以替换的值
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
