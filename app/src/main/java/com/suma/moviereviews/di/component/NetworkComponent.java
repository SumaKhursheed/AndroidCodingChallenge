package com.suma.moviereviews.di.component;

import com.suma.moviereviews.di.module.AppModule;
import com.suma.moviereviews.di.module.NetworkModule;
import com.suma.moviereviews.ui.mvp.detailpage.DetailFragment;
import com.suma.moviereviews.ui.mvp.homepage.HomeFragment;
import com.suma.moviereviews.ui.mvp.reviewpage.ReviewFragment;
import com.suma.moviereviews.ui.mvp.splashpage.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Suma on 6/23/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void injectSplashActivity(SplashActivity splashActivity);

    void injectHomeFragment(HomeFragment homeFragment);

    void injectReviewFragment(ReviewFragment reviewFragment);

    void injectDetailFragment(DetailFragment detailFragment);
}
