package com.udacity.gradle.builditbigger;

/**
 * Created by sheshloksamal on 01/08/16.
 */
public interface JokeAsyncTaskListener {

    void onComplete(String joke, Exception e);

//    void setProgress(int value);
}
