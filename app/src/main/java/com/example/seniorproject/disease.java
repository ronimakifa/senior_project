package com.example.seniorproject;

public class disease {
    String name;
    String description;
    int picture;
    String treatment;

    public disease(String name, String description, int picture, String treatment) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.treatment = treatment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
