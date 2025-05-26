package com.example.seniorproject;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents a plant with its basic properties such as name, watering frequency, soil type, and picture.
 * Implements Serializable for easy passing between activities.
 *
 * @author Roni Zuckerman
 */
public class plant implements Serializable {
    /** The name of the plant. */
    private String name;
    /** The watering frequency for the plant (e.g., "Once a week"). */
    private String wateringFrequency;
    /** The type of soil suitable for the plant. */
    private String soilType;
    /** The URL or path to the plant's picture. */
    private String picture;

    /**
     * Constructs a new plant with all properties specified.
     *
     * @param name The name of the plant.
     * @param wateringFrequency The watering frequency for the plant.
     * @param soilType The type of soil suitable for the plant.
     * @param picture The URL or path to the plant's picture.
     */
    public plant(String name, String wateringFrequency, String soilType, String picture) {
        this.name = name;
        this.wateringFrequency = wateringFrequency;
        this.soilType = soilType;
        this.picture = picture;
    }

    /**
     * Constructs a new plant from a map, typically used when loading from a database.
     *
     * @param map A map containing plant properties with keys: "name", "wateringFrequency", "soilType", "picture".
     */
    public plant(Map map){
        this.name = (String) map.get("name");
        this.wateringFrequency = (String) map.get("wateringFrequency");
        this.soilType = (String) map.get("soilType");
        this.picture = (String) map.get("picture");
    }

    /**
     * Sets the name of the plant.
     *
     * @param name The new name for the plant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the plant.
     *
     * @return The plant's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the watering frequency for the plant.
     *
     * @param wateringFrequency The new watering frequency.
     */
    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    /**
     * Gets the watering frequency for the plant.
     *
     * @return The watering frequency.
     */
    public String getWateringFrequency() {
        return wateringFrequency;
    }

    /**
     * Sets the soil type for the plant.
     *
     * @param soilType The new soil type.
     */
    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    /**
     * Gets the soil type for the plant.
     *
     * @return The soil type.
     */
    public String getSoilType() {
        return soilType;
    }

    /**
     * Sets the picture URL or path for the plant.
     *
     * @param picture The new picture URL or path.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Gets the picture URL or path for the plant.
     *
     * @return The picture URL or path.
     */
    public String getPicture() {
        return picture;
    }
}