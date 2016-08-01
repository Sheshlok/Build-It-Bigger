package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.jokedisplay.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.JokeAsyncTask;
import com.udacity.gradle.builditbigger.JokeAsyncTaskListener;
import com.udacity.gradle.builditbigger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment implements JokeAsyncTaskListener{

    @BindView(R.id.joke_container) RelativeLayout mJokeContainer;
    @BindView(R.id.pb_loading) ProgressBar mPbLoading;
    private InterstitialAd mIntersitialAd;
    private Unbinder mUnbinder;
    private String mJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, rootView);

        mIntersitialAd = new InterstitialAd(getActivity());
        mIntersitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mIntersitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                showJoke();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mIntersitialAd.loadAd(adRequest);
    }

    @OnClick(R.id.display_joke_button)
    public void onDisplayJokeClicked(Button button) {
        showLoadingIndicator(true);
        new JokeAsyncTask().execute(MainActivityFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mJokeContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onComplete(String joke, Exception e) {
        showLoadingIndicator(false);
        if (TextUtils.isEmpty(joke)) {
            Toast.makeText(getActivity(), "Could not get joke", Toast.LENGTH_SHORT).show();
            return;
        }
        if (e != null) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mJoke = joke;

        if (mIntersitialAd != null && mIntersitialAd.isLoaded()) {
            mIntersitialAd.show();
        } else {
            showJoke();
        }
    }

//    @Override
//    public void setProgress(int value) {
//        Log.e("MAF", "" + value);
//        mPbLoading.setProgress(value);
//    }

    private void showJoke() {
        Intent jokeDisplayIntent = new Intent(getActivity(), JokeDisplayActivity.class);
        jokeDisplayIntent.putExtra(JokeDisplayActivity.DISPLAY_JOKE_KEY, mJoke);
        startActivity(jokeDisplayIntent);
    }


    private void showLoadingIndicator(boolean showProgress) {
        mJokeContainer.setVisibility(View.GONE);
        mPbLoading.setVisibility(showProgress ? View.VISIBLE: View.GONE);
    }


}
