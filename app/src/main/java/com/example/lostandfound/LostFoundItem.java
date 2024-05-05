package com.example.lostandfound;

import java.io.Serializable;
import java.util.UUID;

public class LostFoundItem implements Serializable {


    private String id;

    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private String postType;

    public LostFoundItem(String name, String phone, String description, String date, String location, String postType){
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.postType = postType;
        this.id = UUID.randomUUID().toString();
    }

    public LostFoundItem(String name, String phone, String description, String date, String location, String postType, String UUID){
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.postType = postType;
        this.id = UUID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getPostType() {
        return postType;
    }

}
