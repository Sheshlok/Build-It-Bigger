package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sheshloksamal on 01/08/16.
 *
 */
public class JokeAsyncTaskTest extends AndroidTestCase implements JokeAsyncTaskListener {

    private String mJoke;
    private Exception mException;
    private CountDownLatch mCountdownLatchSignal;

    @Override
    protected void setUp() throws Exception {
        mCountdownLatchSignal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        mCountdownLatchSignal.countDown();
    }

    public void testJokeAsyncTask() throws InterruptedException {
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();

        jokeAsyncTask.execute(this);
        mCountdownLatchSignal.await();

        assertNull("An exception occurred", this.mException);
        assertNotNull("Joke is null.", this.mJoke);
        assertFalse("Empty Joke received.", TextUtils.isEmpty(this.mJoke));
    }

    @Override
    public void onComplete(String joke, Exception e) {
        this.mJoke = joke;
        this.mException = e;
        this.mCountdownLatchSignal.countDown();
    }

//    @Override
//    public void setProgress(int value) {
//        assertTrue("Task under progress", value >=0 && value <= 100);
//    }
}
