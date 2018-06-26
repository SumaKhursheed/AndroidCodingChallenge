package com.suma.moviereviews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import timber.log.Timber;

/**
 * Created by Suma on 6/23/2018.
 */

public abstract class ConnectivityHelper {

     //Check for the availability of Internet
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        try {
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
                return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
            }
            else {
                Timber.w(context.getClass().getSimpleName(), "ConnectivityManager is null");
            }
        }
        catch (NullPointerException e) {
            Timber.w(context.getClass().getSimpleName(), e.getMessage());
        }
        return false;
    }
}