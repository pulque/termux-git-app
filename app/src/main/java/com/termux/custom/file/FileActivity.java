package com.termux.custom.file;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.termux.R;

/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.file
 */
public class FileActivity extends AppCompatActivity {

    public static final String TAG_PATH_FILE = "file_activity_tag_path_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);

        if (savedInstanceState == null) {
            String path = "";
            Intent intent = getIntent();
            if (intent != null) {
                path = intent.getStringExtra(TAG_PATH_FILE);
            }
            FileFragment fragment = FileFragment.newInstance();
            fragment.setDefaultPath(path);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();
        }
    }
}
