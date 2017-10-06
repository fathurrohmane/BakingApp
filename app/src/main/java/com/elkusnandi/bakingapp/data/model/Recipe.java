package com.elkusnandi.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    private long id;
    private String title;
    private int serving;
    private List<Ingredient> ingeredients;
    private List<CookingStep> steps;
    private String image;

    public Recipe(long id, String title, int serving, String imagePath) {
        this.id = id;
        this.title = title;
        this.serving = serving;
        ingeredients = new ArrayList<>();
        steps = new ArrayList<>();
        image = imagePath;
    }

    protected Recipe(Parcel in) {
        id = in.readLong();
        title = in.readString();
        serving = in.readInt();
        ingeredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(CookingStep.CREATOR);
        image = in.readString();
    }

    public List<Ingredient> getIngeredients() {
        return ingeredients;
    }

    public void addIngeredients(Ingredient ingeredient) {
        this.ingeredients.add(ingeredient);
    }

    public List<CookingStep> getSteps() {
        return steps;
    }

    public void addSteps(CookingStep step) {
        this.steps.add(step);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getServing() {
        return serving;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeInt(serving);
        dest.writeTypedList(ingeredients);
        dest.writeTypedList(steps);
        dest.writeString(image);
    }
}
