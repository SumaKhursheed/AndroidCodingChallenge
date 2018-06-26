package com.suma.moviereviews.di.module;

import android.app.Application;

import com.suma.moviereviews.di.ReviewApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Suma on 6/23/2018.
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    public ReviewApplication providesReviewApplication() {
        return new ReviewApplication();
    }

}
