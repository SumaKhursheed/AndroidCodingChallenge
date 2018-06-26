package com.suma.moviereviews.model.reviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieInfoResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("num_results")
    @Expose
    private int no;
    @SerializedName("results")
    @Expose
    private List<Results> results = null;

    protected MovieInfoResponse(Parcel in) {
        status = in.readString();
        no = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(no);
    }

    public MovieInfoResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieInfoResponse> CREATOR = new Creator<MovieInfoResponse>() {
        @Override
        public MovieInfoResponse createFromParcel(Parcel in) {
            return new MovieInfoResponse(in);
        }

        @Override
        public MovieInfoResponse[] newArray(int size) {
            return new MovieInfoResponse[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
