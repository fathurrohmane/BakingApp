package com.elkusnandi.bakingapp.data;

import com.elkusnandi.bakingapp.data.model.CookingStep;
import com.elkusnandi.bakingapp.data.model.Ingredient;
import com.elkusnandi.bakingapp.data.model.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class AppDataManager {

    private static AppDataManager INSTANCE;

    private OkHttpClient mHttpClient;

    private AppDataManager() {
        mHttpClient = new OkHttpClient();
    }

    public static AppDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppDataManager();
        }
        return INSTANCE;
    }

    public Single<List<Recipe>> getRecipes() {
        return Single.create(new SingleOnSubscribe<List<Recipe>>() {
            @Override
            public void subscribe(SingleEmitter<List<Recipe>> e) throws Exception {
                Request request = new Request.Builder()
                        .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                        .build();
                Response response = mHttpClient.newCall(request).execute();
                List<Recipe> recipeList = new ArrayList<>();
                if (response.isSuccessful()) {
                    JSONArray mainResult = new JSONArray(response.body().string());
                    JSONObject jsonObject;
                    for (int i = 0; i < mainResult.length(); i++) {
                        JSONObject jsonRecipie = mainResult.getJSONObject(i);

                        Recipe recipe = new Recipe(
                                jsonRecipie.getLong("id"),
                                jsonRecipie.getString("name"),
                                jsonRecipie.getInt("servings"),
                                jsonRecipie.getString("image")
                        );
                        JSONArray jsonIngredients = jsonRecipie.getJSONArray("ingredients");
                        for (int j = 0; j < jsonIngredients.length(); j++) {
                            jsonObject = jsonIngredients.getJSONObject(j);
                            Ingredient ingredient = new Ingredient(
                                    jsonObject.getString("ingredient"),
                                    jsonObject.getDouble("quantity"),
                                    jsonObject.getString("measure")
                            );
                            recipe.addIngeredients(ingredient);
                        }
                        JSONArray jsonSteps = jsonRecipie.getJSONArray("steps");
                        for (int j = 0; j < jsonSteps.length(); j++) {
                            jsonObject = jsonSteps.getJSONObject(j);
                            CookingStep cookingStep = new CookingStep(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("shortDescription"),
                                    jsonObject.getString("description"),
                                    jsonObject.getString("videoURL"),
                                    jsonObject.getString("thumbnailURL")
                            );
                            recipe.addSteps(cookingStep);
                        }
                        recipeList.add(recipe);
                    }
                    e.onSuccess(recipeList);
                } else {
                    e.onError(new Exception("Request recipe data error"));
                }
            }
        })
                ;
    }
}
