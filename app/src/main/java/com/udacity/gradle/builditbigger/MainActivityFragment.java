package com.udacity.gradle.builditbigger;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeAsyncTaskListener{

    @BindView(R.id.joke_container) RelativeLayout mJokeContainer;
    @BindView(R.id.pb_loading) ProgressBar mPbLoading;
    private Unbinder mUnbinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, rootView);

    }

    @Override
    public void onResume() {
        super.onResume();
        mJokeContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.display_joke_button)
    public void onDisplayJokeClicked(Button button) {
        showLoadingIndicator(true);
        new JokeAsyncTask().execute(MainActivityFragment.this);
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

        showJoke(joke);
    }

//    @Override
//    public void setProgress(int value) {
//        Log.e("MAF ",  "" + value);
//        mPbLoading.setProgress(value);
//    }

    private void showJoke(String joke) {
        Intent jokeDisplayIntent = new Intent(getActivity(), JokeDisplayActivity.class);
        jokeDisplayIntent.putExtra(JokeDisplayActivity.DISPLAY_JOKE_KEY, joke);
        startActivity(jokeDisplayIntent);

    }

    private void showLoadingIndicator(boolean showProgress) {
        mJokeContainer.setVisibility(View.GONE);
        mPbLoading.setVisibility(showProgress ? View.VISIBLE: View.GONE);
    }
}