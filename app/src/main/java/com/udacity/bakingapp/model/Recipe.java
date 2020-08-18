package com.udacity.bakingapp.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("ingredients")
    private final List<Ingredient> ingredients;

    @SerializedName("steps")
    private final List<Instruction> instructions;

    @SerializedName("servings")
    private final int servingSize;

    @SerializedName("image")
    private final String image;

    private int stepIdSelected = 0;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Instruction> instructions, int servingSize, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servingSize = servingSize;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getImage() {
        return image;
    }

    public int getStepIdSelected() {
        return stepIdSelected;
    }

    public void setStepIdSelected(int stepIdSelected) {
        this.stepIdSelected = stepIdSelected;
    }
}
