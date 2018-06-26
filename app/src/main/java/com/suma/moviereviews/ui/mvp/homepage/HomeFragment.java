package com.suma.moviereviews.ui.mvp.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suma.moviereviews.R;
import com.suma.moviereviews.di.ReviewApplication;
import com.suma.moviereviews.di.component.NetworkComponent;
import com.suma.moviereviews.ui.mvp.reviewpage.ReviewFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Suma on 6/23/2018.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.button_go_in)
    Button buttonGoIn;

    private Unbinder unbinder;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        getApplicationComponent().injectHomeFragment(this);

        return view;
    }

    @OnClick({R.id.button_go_in})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).
                getSupportFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        switch (view.getId()) {
            case R.id.button_go_in:
                ReviewFragment fragment = ReviewFragment.newInstance();
                beginFragmentTransaction(transaction, fragment);
                break;
        }
    }

    private void beginFragmentTransaction(FragmentTransaction transaction, Fragment fragment) {
        transaction.replace(R.id.main_frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private NetworkComponent getApplicationComponent() {
        return ((ReviewApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageActivity.toolbarSecondTitle.setText(R.string.homefragment_title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
