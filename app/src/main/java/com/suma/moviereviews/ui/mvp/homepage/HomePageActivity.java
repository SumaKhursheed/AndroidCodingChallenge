package com.suma.moviereviews.ui.mvp.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.suma.moviereviews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Suma on 6/23/2018.
 */

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static TextView toolbarSecondTitle;
    private Fragment fragment;

    public static final String MAIN_FRAGMENT = "MAIN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbarSecondTitle = findViewById(R.id.toolbar_second_title);

        fragment = getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT);
        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.main_frame_layout, fragment,
                    MAIN_FRAGMENT).commit();
        }
        else {
            Timber.d("Fragment is not null...");
        }
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, HomePageActivity.class);
    }

}
