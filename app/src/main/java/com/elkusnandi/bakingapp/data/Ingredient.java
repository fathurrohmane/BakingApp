package com.elkusnandi.bakingapp.data;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class Ingredient {

    private String ingredient;
    private int quantity;
    private String measurement;

    public Ingredient(String ingredient, int quantity, String measurement) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasurement() {
        return measurement;
    }
}
