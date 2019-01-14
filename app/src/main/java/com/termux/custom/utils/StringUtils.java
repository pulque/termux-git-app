package com.termux.custom.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * Created by LiZhe on 2019-01-11.
 * com.termux.custom.utils
 */
public class StringUtils {

    /**
     * 读取文件到字符串
     *
     * @return 文本内容
     */
    public static String readToString(File file) {
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    // 将字符串写入到文本文件中
    public static void writeStringToFile(String content, String filePath, String fileName) {
        try {
            //生成文件夹之后，再生成文件
            File file = makeFilePath(filePath, fileName);
            if (file == null) {
                return;
            }
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + file.getAbsolutePath());
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(content.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    private static File makeFilePath(String filePath, String fileName) throws IOException {
        File file = null;
        makeRootDirectory(filePath);
        file = new File(filePath + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    // 生成文件夹
    private static void makeRootDirectory(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
