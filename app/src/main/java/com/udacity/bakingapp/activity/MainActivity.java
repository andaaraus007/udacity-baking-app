package com.udacity.bakingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.databinding.ActivityMainBinding;
import com.udacity.bakingapp.inteface.RetrofitApiInterface;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.RetrofitApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {
    private GridLayoutManager mGridLayoutManager;
    private RecipeAdapter mAdapter;
    private static List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView recipeListRecyclerView = activityMainBinding.rvRecipes;

        int numberOfColumns;

        if (activityMainBinding.llTablet != null) {
            numberOfColumns = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 4 : 2;

        } else {
            numberOfColumns = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 2 : 1;
        }

        mGridLayoutManager = new GridLayoutManager (this, numberOfColumns);
        recipeListRecyclerView.setLayoutManager(mGridLayoutManager);
        recipeListRecyclerView.setHasFixedSize(true);

        mRecipeList = new ArrayList<>();
        mAdapter = new RecipeAdapter(mRecipeList, recipeListRecyclerView, this);
        recipeListRecyclerView.setAdapter(mAdapter);

        loadRecipeData();
    }

    private void loadRecipeData() {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<List<Recipe>> recipeList =  retrofitApiInterface.getAllRecipes();
        recipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {

            }
        });
    }

    private void loadData(List<Recipe> recipes) {
        mAdapter.addRecipeData(recipes);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Context context = MainActivity.this;
        Class<RecipeActivity> destinationActivity = RecipeActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        Recipe recipe = mRecipeList.get(clickedItemIndex);
        startChildActivityIntent.putExtra(String.valueOf(R.string.app_name_recipe_detail), recipe);
        startActivity(startChildActivityIntent);
    }
}