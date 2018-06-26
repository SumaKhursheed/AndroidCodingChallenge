package com.suma.moviereviews.model;

import com.suma.moviereviews.model.reviews.MovieInfoResponse;

/**
 * Created by Suma on 6/23/2018.
 */

public abstract class ModelMapper {

    public static MovieInfoResponse toMovieInfoResponseModel(MovieInfoResponse movieInfoResponse) {
        MovieInfoResponse response = new MovieInfoResponse();

        response.setStatus(movieInfoResponse.getStatus());
        response.setNo(movieInfoResponse.getNo());
        response.setResults(movieInfoResponse.getResults());

        return response;
    }
}