package com.suma.moviereviews.ui.mvp;

/**
 * Created by Suma on 6/24/2018.
 */

public interface Presenter<T extends ViewInterface> {

    void attachView(T t);

    void detachView();
}