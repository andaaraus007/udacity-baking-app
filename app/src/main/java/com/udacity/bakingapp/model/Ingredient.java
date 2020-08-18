package com.udacity.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {
    @SerializedName("quantity")
    private final String quantity;

    @SerializedName("measure")
    private final String measurement;

    @SerializedName("ingredient")
    private final String name;

    public Ingredient(String quantity, String measurement, String name) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    public String getIngredientDescription() {
        return quantity + " " + measurement + " of " + name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getName() {
        return name;
    }
}
