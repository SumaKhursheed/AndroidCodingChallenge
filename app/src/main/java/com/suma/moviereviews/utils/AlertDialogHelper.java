package com.suma.moviereviews.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.suma.moviereviews.R;
import com.suma.moviereviews.ui.mvp.homepage.NoInternetActivity;
import com.suma.moviereviews.ui.mvp.splashpage.SplashActivity;

/**
 * Created by Suma on 6/23/2018.
 */

public abstract class AlertDialogHelper {

     // Method to create Alert Dialog if no internet is present
    public static void createNoInternetDialog(@NonNull final Context context, int title) {
        new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .title(title)
                .content(R.string.no_internet)
                .cancelable(false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (context instanceof SplashActivity) {
                            openNoInternetPage(context);
                            ((SplashActivity) context).finish();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (context instanceof SplashActivity) {
                            ((SplashActivity) context).finish();
                        }
                    }
                })
                .show();
    }

    private static void openNoInternetPage(Context context) {
        context.startActivity(NoInternetActivity.newInstance(context));
    }
}