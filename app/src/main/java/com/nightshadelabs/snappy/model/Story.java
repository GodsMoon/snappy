package com.nightshadelabs.snappy.model;

import java.io.Serializable;

/**
 * Created by davidshellabarger on 6/11/16.
 */
public class Story implements Serializable {

    public static String BUNDLE_ID = "STORY";
    String name;
    String id;

    public Story(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
