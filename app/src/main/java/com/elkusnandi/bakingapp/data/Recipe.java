package com.elkusnandi.bakingapp.data;

import java.util.List;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class Recipe {

    private String title;
    private int serving;
    private List<Ingredient> ingeredients;
    private List<CookingStep> steps;

    public Recipe(String title, int serving, List<Ingredient> ingeredients, List<CookingStep> steps) {
        this.title = title;
        this.serving = serving;
        this.ingeredients = ingeredients;
        this.steps = steps;
    }


}
