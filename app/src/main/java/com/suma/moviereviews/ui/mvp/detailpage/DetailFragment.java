package com.suma.moviereviews.ui.mvp.detailpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.suma.moviereviews.R;
import com.suma.moviereviews.di.ReviewApplication;
import com.suma.moviereviews.di.component.NetworkComponent;
import com.suma.moviereviews.ui.mvp.homepage.HomePageActivity;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Suma on 6/24/2018.
 */

public class DetailFragment extends Fragment{

    public static final String MOVIE_NAME = "movieName";
    public static final String IMAGE = "image";
    public static final String RATING = "rating";
    public static final String REVIEWER = "reviewer";
    public static final String DATE = "date";
    public static final String SUMMARY = "summary";
    public static final String LINK = "link";

    private Unbinder unbinder;
    private String moviename, imagename, moviesummary,
            reviewername, movierating, movielink, reviewdate;

    @BindView(R.id.movie_image)
    ImageView image;
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.reviewer)
    TextView reviewer;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.link)
    TextView link;
    @BindView(R.id.date)
    TextView date;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetworkComponent().injectDetailFragment(this);
    }

    private NetworkComponent getNetworkComponent() {
        return ((ReviewApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageActivity.toolbarSecondTitle.setText(R.string.detail);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        getBundleDetails();
        detailView();
        return view;
    }

    private void getBundleDetails() {
        if (getArguments() != null) {
            imagename = getArguments().getString(IMAGE);
            moviename = getArguments().getString(MOVIE_NAME);
            moviesummary = getArguments().getString(SUMMARY);
            reviewdate = getArguments().getString(DATE);
            movielink = getArguments().getString(LINK);
            reviewername = getArguments().getString(REVIEWER);
            movierating = getArguments().getString(RATING);
        }
        else {
            imagename = String.valueOf(getResources().getDrawable(R.drawable.camera));
            moviename = "Movie Name";
            moviesummary = "Summary";
            reviewdate = "Date";
            movielink = "Movie Link";
            reviewername = "Reviewer";
            movierating = "Rating";
        }
    }

    public void detailView() {
        movieName.setText(moviename);
        summary.setText(moviesummary);
        date.setText(reviewdate);
        Picasso.with(getActivity()).load(imagename).fit().into(image);
        link.setText(movielink);
        Linkify.addLinks(link, Linkify.WEB_URLS);
        reviewer.setText(reviewername);
        if (movierating.equals("")) {
            rating.setText(R.string.no_current_ratings);
        }
        else
        {
            rating.setText(movierating);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
