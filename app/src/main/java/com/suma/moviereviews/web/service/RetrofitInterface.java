package com.suma.moviereviews.web.service;

import com.suma.moviereviews.model.reviews.MovieInfoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Suma on 6/23/2018.
 */

public interface RetrofitInterface {

    @GET("dvd-picks.json")
    Call<MovieInfoResponse> getMovieInfo(@QueryMap Map<String, Object> queries);

}
