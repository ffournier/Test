package com.appstud.testappstud.network;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Contacts {

    @SerializedName("contacts")
    public ArrayList<Contact> contacts = new ArrayList<>();

    /**
     * "firstname" : "Antoine",
     * "lastname" : "Pemeja",
     * "status" : "Redmine Addict <3",
     * "hisface" : "antoine.jpg"
     */
    public class Contact  {

        @SerializedName("firstname")
        public String firstname = null;

        @SerializedName("lastname")
        public String lastname = null;

        @SerializedName("status")
        public String status = null;

        @SerializedName("hisface")
        public String hisface = null;
    }
}
