package com.suma.moviereviews.ui.mvp.splashpage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import com.suma.moviereviews.R;
import com.suma.moviereviews.di.ReviewApplication;
import com.suma.moviereviews.di.component.NetworkComponent;
import com.suma.moviereviews.model.ModelMapper;
import com.suma.moviereviews.model.reviews.MovieInfoResponse;
import com.suma.moviereviews.ui.mvp.homepage.HomePageActivity;
import com.suma.moviereviews.utils.AlertDialogHelper;
import com.suma.moviereviews.utils.ConnectivityHelper;
import com.suma.moviereviews.web.service.RetrofitInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by Suma on 6/23/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1500;

    @Inject
    ReviewApplication reviewApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getApplicationComponent().injectSplashActivity(this);

        // check for internet connectivity
        if (ConnectivityHelper.isInternetAvailable(this)) {
            navigateToHome();
        }
        else {
            AlertDialogHelper.createNoInternetDialog(this, R.string.network_error);
        }
    }

    private void navigateToHome() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(HomePageActivity.newInstance(SplashActivity.this));
                startTransitionAnimation();
                finish();
                handler.removeCallbacks(this);
            }
        }, SPLASH_TIME);
    }

    private void startTransitionAnimation() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private NetworkComponent getApplicationComponent() {
        return ((ReviewApplication) getApplication()).getNetworkComponent();
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, SplashActivity.class);
    }
}
