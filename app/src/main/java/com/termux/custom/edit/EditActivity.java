package com.termux.custom.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.termux.R;

/**
 * Created by LiZhe on 2019-01-10.
 * com.termux.custom.edit
 */
public class EditActivity extends AppCompatActivity {

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
