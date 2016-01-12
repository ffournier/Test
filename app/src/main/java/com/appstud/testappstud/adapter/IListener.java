package com.appstud.testappstud.adapter;


import com.appstud.testappstud.database.MyContact;

/**
 * Listener for Adapter generic
 */
public interface IListener {
    void onClick(MyContact contact, int position);
}
