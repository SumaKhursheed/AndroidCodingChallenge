package com.suma.moviereviews.ui.mvp.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.suma.moviereviews.R;
import com.suma.moviereviews.ui.mvp.splashpage.SplashActivity;
import com.suma.moviereviews.utils.AlertDialogHelper;
import com.suma.moviereviews.utils.ConnectivityHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Suma on 6/23/2018.
 */

public class NoInternetActivity extends AppCompatActivity {

    @BindView(R.id.button_retry)
    Button buttonRetry;
    private static Context mContext;

    public static Intent newInstance(Context context) {
        mContext = context;
        return new Intent(context, NoInternetActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_retry)
    public void onViewClicked() {
        if (ConnectivityHelper.isInternetAvailable(this)) {
            if (mContext instanceof SplashActivity) {
                startActivity(SplashActivity.newInstance(this));
                finish();
            }
        }
        else {
            AlertDialogHelper.createNoInternetDialog(this, R.string.network_error);
        }
    }
}