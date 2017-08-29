package com.elkusnandi.bakingapp.data;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class CookingStep {

    private int id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String tumbnailUrl;

    public CookingStep(int id, String shortDescription, String description, String videoUrl, String tumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.tumbnailUrl = tumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTumbnailUrl() {
        return tumbnailUrl;
    }
}
