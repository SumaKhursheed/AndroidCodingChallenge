package com.suma.moviereviews.ui.mvp.reviewpage;

import android.content.Context;
import android.support.annotation.NonNull;

import com.suma.moviereviews.di.ReviewApplication;
import com.suma.moviereviews.model.ModelMapper;
import com.suma.moviereviews.model.reviews.MovieInfoResponse;
import com.suma.moviereviews.model.reviews.Results;
import com.suma.moviereviews.ui.Constants;
import com.suma.moviereviews.ui.mvp.Presenter;
import com.suma.moviereviews.web.service.RetrofitInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ReviewPresenter implements Presenter<ReviewViewInterface> {

    private ReviewViewInterface reviewViewInterface;
    private Call<MovieInfoResponse> movieInfoCall;
    private Retrofit retrofit;

    private static final String API_KEY = "b75da00e12d54774a2d362adddcc9bef";

    private ReviewApplication reviewApplication;

    public ReviewPresenter(ReviewViewInterface reviewViewInterface, Retrofit retrofit,
                           ReviewApplication reviewApplication) {
        this.reviewViewInterface = reviewViewInterface;
        this.retrofit = retrofit;
        this.reviewApplication = reviewApplication;
    }

    public ReviewPresenter() {
    }

    public void fetchMovieInfo() {
        showProgress();

        Retrofit retrofit1 = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
//                .client(httpClient)
                .baseUrl(Constants.BASE_URL)
                .build();
        RetrofitInterface retrofitInterface = retrofit1.create(RetrofitInterface.class);

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("order", Constants.ORDERS);
        queryMap.put("api-key", API_KEY);

        movieInfoCall = retrofitInterface.getMovieInfo(
                queryMap);
        movieInfoCall.enqueue(new Callback<MovieInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieInfoResponse> call,
                                   @NonNull Response<MovieInfoResponse> response) {
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus().equals("OK")) {
                        onSuccess(response.body());
                    }

                    else {
                        Timber.e(getClass().getSimpleName(),
                                "Wrong code: " + response.code());
                    }
                }
                else {
                    Timber.d(getClass().getSimpleName(), "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieInfoResponse> call, @NonNull Throwable t) {
                Timber.e(getClass().getSimpleName(),
                        "Retrofit Failure: " + t.getMessage());
            }
        });
    }

    private void onSuccess(MovieInfoResponse movieInfoResponse) {
        reviewApplication.setMovieInfo(ModelMapper.toMovieInfoResponseModel(movieInfoResponse));

        ArrayList<Results> resultList = new ArrayList<>(reviewApplication.getMovieInfo().getResults());
        reviewViewInterface.setUpMovieInfoRecyclerview(resultList);
        hideProgress();
    }

    private void showProgress() {
        reviewViewInterface.showProgress();
    }

    private void hideProgress() {
        reviewViewInterface.hideProgress();
    }

    @Override
    public void attachView(ReviewViewInterface reviewViewInterface) {
        this.reviewViewInterface = reviewViewInterface;
    }

    @Override
    public void detachView() {
        Timber.d("--- detach View");
        this.reviewViewInterface = null;
        if (movieInfoCall != null && !movieInfoCall.isCanceled()) {
            Timber.d("--- productCall cancelled");
            movieInfoCall.cancel();
        }
    }

}
