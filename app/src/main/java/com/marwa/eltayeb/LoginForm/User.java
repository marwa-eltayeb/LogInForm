package com.marwa.eltayeb.LoginForm;

/**
 * Created by Marwa on 10/15/2018.
 */

public class User {

    /**
     * Name of the user
     */
    private String name;

    /**
     * Address of the user
     */
    private String address;

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
