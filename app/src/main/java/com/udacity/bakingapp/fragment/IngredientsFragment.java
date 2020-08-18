package com.udacity.bakingapp.fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.databinding.FragmentIngredientsBinding;
import com.udacity.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientsFragment extends Fragment {
    List<Ingredient> mIngredients;
    FragmentIngredientsBinding mFragmentIngredientsBinding;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        mFragmentIngredientsBinding = DataBindingUtil.bind(rootView);
        setView();

        return rootView;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void setView() {
        TextView ingredientsTextView;
        TextView ingredientsTitleTextView;

        if (mFragmentIngredientsBinding != null) {
            ingredientsTextView = mFragmentIngredientsBinding.tvIngredients;
            ingredientsTitleTextView = mFragmentIngredientsBinding.tvIngredientsTitle;
            ingredientsTitleTextView.setTypeface(null, Typeface.BOLD);
            setIngredientTextView(ingredientsTextView);
        }

    }

    private void setIngredientTextView(TextView ingredientsTextView) {
        ingredientsTextView.setText("");
        if (mIngredients != null) {
            if (mIngredients.size() > 0) {
                int index = 1;
                for (Ingredient ingredient : mIngredients) {
                    ingredientsTextView.append(ingredient.getIngredientDescription());
                    if (index < mIngredients.size()) {
                        ingredientsTextView.append("\n");
                    }
                }
            }
        }
    }
}