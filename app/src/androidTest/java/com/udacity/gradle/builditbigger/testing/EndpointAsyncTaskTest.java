package com.udacity.gradle.builditbigger.testing;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.EndpointAsyncTask;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EndpointAsyncTaskTest {


    @Test
    public void getNotEmpty() {

        EndpointAsyncTask asyncTask = new EndpointAsyncTask(null);

        String result = null;

        try {
            asyncTask.execute();
            result = asyncTask.get();
        }
        catch (Exception ex) { }

        Assert.assertTrue("Empty message was returned!", !TextUtils.isEmpty(result));

    }
}