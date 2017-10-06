package com.elkusnandi.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class CookingStep implements Parcelable {

    public static final Creator<CookingStep> CREATOR = new Creator<CookingStep>() {
        @Override
        public CookingStep createFromParcel(Parcel in) {
            return new CookingStep(in);
        }

        @Override
        public CookingStep[] newArray(int size) {
            return new CookingStep[size];
        }
    };
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

    protected CookingStep(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        tumbnailUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(tumbnailUrl);
    }
}
