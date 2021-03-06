package com.appstud.testappstud.database;

// THIS CODE IS GENERATED BY greenDA

/**
 * Entity mapped to table CONTACT.
 */
public class MyContact {


    private Long id;
    /** Not-null value. */
    private String name;
    private String status;
    private String hisface;


    public MyContact() {
    }

    public MyContact(Long id) {
        this.id = id;
    }

    public MyContact(Long id, String name, String status, String hisface) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.hisface = hisface;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHisFace() {
        return hisface;
    }

    public void setHisFace(String hisface) {
        this.hisface = hisface;
    }

}
