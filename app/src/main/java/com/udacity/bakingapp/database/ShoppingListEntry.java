package com.udacity.bakingapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppingList")
public class ShoppingListEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private String quantity;
    private String measurement;
    private String name;

    @Ignore
    public ShoppingListEntry(int recipeId, String quantity, String measurement, String name) {
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    public ShoppingListEntry(int id, int recipeId, String quantity, String measurement, String name) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
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
