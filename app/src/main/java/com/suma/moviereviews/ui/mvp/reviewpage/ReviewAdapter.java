package com.suma.moviereviews.ui.mvp.reviewpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.suma.moviereviews.R;
import com.suma.moviereviews.model.reviews.Results;
import com.suma.moviereviews.ui.mvp.detailpage.DetailFragment;
import com.suma.moviereviews.ui.mvp.homepage.HomePageActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.suma.moviereviews.ui.mvp.homepage.HomePageActivity.MAIN_FRAGMENT;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Results> movieInfoList;

    public ReviewAdapter(Context context, ArrayList<Results> movieInfoList) {
        this.context = context;
        this.movieInfoList = movieInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindData(movieInfoList.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timber.d(getClass().getName(), "On click triggered.");
                Bundle bundle = getBundleDetails(holder);
                DetailFragment fragment = DetailFragment.newInstance();
                fragment.setArguments(bundle);
                ((HomePageActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, fragment, MAIN_FRAGMENT)
                        .addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    private Bundle getBundleDetails(ViewHolder holder) {
        Bundle bundle = new Bundle();
        bundle.putString("image",
                movieInfoList.get(holder.getAdapterPosition()).getMultimedia().getSource());
        bundle.putString("movieName",
                movieInfoList.get(holder.getAdapterPosition()).getTitle());
        bundle.putString("rating", movieInfoList.get(holder.getAdapterPosition()).getRating());
        bundle.putString("reviewer", movieInfoList.get(holder.getAdapterPosition()).getReviewer());
        bundle.putString("date", movieInfoList.get(holder.getAdapterPosition()).getDate());
        bundle.putString("summary", movieInfoList.get(holder.getAdapterPosition()).getSummary());
        bundle.putString("link", movieInfoList.get(holder.getAdapterPosition()).getLink().getUrl());
        return bundle;
    }

    @Override
    public int getItemCount() {
        return movieInfoList != null ? movieInfoList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail_movie)
        ImageView imageView;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.headline)
        TextView reviewHeadline;
        @BindView(R.id.reviewer)
        TextView movieReviewer;
        @BindView(R.id.rating)
        TextView movieRating;
        @BindView(R.id.date)
        TextView reviewDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Results movieInfoResponse) {
            setName(movieInfoResponse.getTitle());
            setHeadline(movieInfoResponse.getHeadline());
            setRating(movieInfoResponse.getRating());
            setReviewer(movieInfoResponse.getReviewer());
            setDate(movieInfoResponse.getDate());
            setImage(movieInfoResponse);
        }

        private void setDate(String date) {
            reviewDate.setText(date);
        }

        private void setReviewer(String reviewer) {
            movieReviewer.setText(reviewer);
        }

        private void setRating(String rating) {
            movieRating.setText(rating);
        }

        private void setHeadline(String headline) {
            reviewHeadline.setText(headline);
        }

        private void setName(String name) {
            movieName.setText(name);
        }

        private void setImage(Results response) {
            if (response.getMultimedia().getSource() != null) {
                String productImageUrl = response.getMultimedia().getSource();
                Picasso.with(context).load(productImageUrl).fit()
                        .into(imageView);
            }
            else {
                setImageLocally();
            }
        }

        private void setImageLocally() {
            Picasso.with(context).load(R.drawable.camera).fit().into(imageView);
        }
    }
}
