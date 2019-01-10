package com.termux.custom.file;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.termux.R;
import com.termux.custom.edit.EditFragment;

/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.file
 */
public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);

        if (savedInstanceState == null) {
            EditFragment fragment = EditFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();
        }
    }
}
