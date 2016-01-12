package com.appstud.testappstud;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.appstud.testappstud.network.Contacts;
import com.appstud.testappstud.network.INetwork;
import com.appstud.testappstud.network.NetworkCore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    INetwork network;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        NetworkCore core = NetworkCore.getInstance(getContext());
        network = core.getRestAdapter().create(INetwork.class);

    }

    public void testContact() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        network.contacts(new Callback<Contacts>() {
            @Override
            public void success(Contacts contacts, Response response) {
                assertTrue(contacts != null);
                latch.countDown();
            }

            @Override
            public void failure(RetrofitError error) {
                assertFalse(true);
                latch.countDown();
            }

        });

        latch.await(20000, TimeUnit.MILLISECONDS);
    }


}