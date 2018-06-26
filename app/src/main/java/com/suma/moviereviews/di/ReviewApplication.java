package com.suma.moviereviews.di;

import android.app.Application;

import com.suma.moviereviews.di.component.DaggerNetworkComponent;
import com.suma.moviereviews.di.component.NetworkComponent;
import com.suma.moviereviews.di.module.AppModule;
import com.suma.moviereviews.di.module.NetworkModule;
import com.suma.moviereviews.model.reviews.MovieInfoResponse;

import timber.log.Timber;

/**
 * Created by Suma on 6/23/2018.
 */

public class ReviewApplication extends Application {

    private String BASE_URL = "http://api.nytimes.com/svc/movies/v2/reviews/dvd-picks.json/";

    private NetworkComponent networkComponent;
    private static ReviewApplication instance;

    private MovieInfoResponse movieInfo;

    public ReviewApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        initializeComponent();
        instance = this;
    }

    private void initializeComponent() {
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BASE_URL))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public MovieInfoResponse getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfoResponse merchantInfo) {
        this.movieInfo = merchantInfo;
    }
}
