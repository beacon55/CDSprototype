package com.optimationgroup.cds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.sdk.SystemRequirementsChecker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Estimote convenience method: Checks if all requirements are met for beacon detection across
        all Android versions and provides default dialogs for asking for permissions and access.
         */
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

}
