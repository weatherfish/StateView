package com.github.nukc.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.github.nukc.stateview.StateView;

public class InjectActivity extends AppCompatActivity {

    private static final String TAG = InjectActivity.class.getSimpleName();

    private StateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject);

        mStateView = StateView.inject(this, true);

        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                //do something
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_inject, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_empty:
                mStateView.showEmpty();
                break;
            case R.id.show_retry:
                mStateView.showRetry();
                break;
            case R.id.show_content:
                mStateView.showContent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
