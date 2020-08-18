package com.udacity.bakingapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM shoppingList")
    List<ShoppingListEntry> loadAllIngredients();

    @Query("DELETE FROM shoppingList")
    void deleteAllIngredients();

    @Insert
    void insertIngredient(ShoppingListEntry ingredient);
}
