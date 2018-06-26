package com.suma.moviereviews.ui.mvp.reviewpage;

import com.suma.moviereviews.model.reviews.Results;
import com.suma.moviereviews.ui.mvp.ViewInterface;

import java.util.ArrayList;

/**
 * Created by Suma on 6/24/2018.
 */

public interface ReviewViewInterface extends ViewInterface {

    void showProgress();

    void hideProgress();

    void setUpMovieInfoRecyclerview(ArrayList<Results> resultList);
}
