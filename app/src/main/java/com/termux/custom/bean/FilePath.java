package com.termux.custom.bean;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by LiZhe on 2019-01-11.
 * com.termux.custom.bean
 */
public class FilePath {

    public FilePath() {
        this.files = new ArrayList<>();
    }

    public FilePath(File file) {
        this.file = file;
        this.files = new ArrayList<>();
    }

    public boolean isSelect;
    public File file;
    public ArrayList<FilePath> files;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<FilePath> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FilePath> files) {
        this.files = files;
    }
}
