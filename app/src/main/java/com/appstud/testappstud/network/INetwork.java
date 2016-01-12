package com.appstud.testappstud.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Service
 * TODO need to create a manager to refresh the token if is in invalidate :)
 */
public interface INetwork {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("/contacts.json")
    void contacts(
               Callback<Contacts> callback);

}
