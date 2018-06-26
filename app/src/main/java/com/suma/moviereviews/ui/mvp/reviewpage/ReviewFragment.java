package com.suma.moviereviews.ui.mvp.reviewpage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.suma.moviereviews.R;
import com.suma.moviereviews.di.ReviewApplication;
import com.suma.moviereviews.di.component.NetworkComponent;
import com.suma.moviereviews.model.reviews.Results;
import com.suma.moviereviews.ui.mvp.homepage.HomePageActivity;
import com.suma.moviereviews.utils.AlertDialogHelper;
import com.suma.moviereviews.utils.ConnectivityHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Suma on 6/23/2018.
 */

public class ReviewFragment extends Fragment implements ReviewViewInterface{

    private ReviewPresenter reviewPresenter;
    private Unbinder unbinder;
    private ProgressDialog dialog;
    private ArrayList<Results> movieInfoList;
    private ReviewAdapter reviewAdapter;

    @Inject
    Retrofit retrofit;

    @Inject
    ReviewApplication reviewApplication;

    @BindView(R.id.review_recycler)
    RecyclerView movieRecyclerView;
    @BindView(R.id.dashboard_progress_bar)
    ProgressBar dashboardProgressBar;

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageActivity.toolbarSecondTitle.setText(R.string.reviewpage_title);
        fetchReviewList();

    }

    private void fetchReviewList() {
        if (ConnectivityHelper.isInternetAvailable(getActivity())) {
            reviewPresenter = new ReviewPresenter(this, retrofit, reviewApplication);
            reviewPresenter.fetchMovieInfo();
        }
        else {
            hideProgress();
            AlertDialogHelper.createNoInternetDialog(getActivity(), R.string.network_error);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        reviewPresenter = new ReviewPresenter();
        getNetworkComponent().injectReviewFragment(this);

        initView();

        return view;
    }

    private void initView() {
        reviewPresenter.attachView(this);
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Connecting...");
        dialog.setMessage("Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    private NetworkComponent getNetworkComponent() {
        return ((ReviewApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void setUpMovieInfoRecyclerview(ArrayList<Results> resultList) {
        movieInfoList = resultList;
        reviewAdapter = new ReviewAdapter(getActivity(), movieInfoList);
        movieRecyclerView.setAdapter(new ScaleInAnimationAdapter(reviewAdapter));
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_movies", movieInfoList);
    }

    @Override
    public void showProgress() {
        dashboardProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        dashboardProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Timber.d("--- in onDestroyView");
        reviewPresenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("--- in onStop");
        hideProgress();
        reviewPresenter.detachView();
    }
}
