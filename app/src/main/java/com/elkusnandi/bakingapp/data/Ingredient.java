package com.elkusnandi.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class Ingredient implements Parcelable {

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    private String ingredient;
    private double quantity;
    private String measurement;

    public Ingredient(String ingredient, double quantity, String measurement) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    protected Ingredient(Parcel in) {
        ingredient = in.readString();
        quantity = in.readDouble();
        measurement = in.readString();
    }

    public String getIngredient() {
        return ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeDouble(quantity);
        dest.writeString(measurement);
    }
}
