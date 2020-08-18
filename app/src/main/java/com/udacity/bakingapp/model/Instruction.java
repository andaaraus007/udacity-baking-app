package com.udacity.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instruction implements Serializable {
    @SerializedName("id")
    private final int id;

    @SerializedName("shortDescription")
    private final String shortDescription;

    @SerializedName("description")
    private final String description;

    @SerializedName("videoURL")
    private final String videoUrl;

    @SerializedName("thumbnailURL")
    private final String thumbnailUrl;

    public Instruction(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
