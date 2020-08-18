package com.udacity.bakingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.database.AppDatabase;
import com.udacity.bakingapp.database.ShoppingListEntry;
import com.udacity.bakingapp.databinding.ActivityRecipeBinding;
import com.udacity.bakingapp.fragment.IngredientsFragment;
import com.udacity.bakingapp.fragment.InstructionFragment;
import com.udacity.bakingapp.fragment.InstructionsFragment;
import com.udacity.bakingapp.fragment.VideoFragment;
import com.udacity.bakingapp.inteface.RetrofitApiInterface;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Instruction;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.AppExecutors;
import com.udacity.bakingapp.utils.MediaUtils;
import com.udacity.bakingapp.utils.RetrofitApiClient;
import com.udacity.bakingapp.utils.ScreenUtils;
import com.udacity.bakingapp.widget.BakingAppWidgetProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity implements InstructionsFragment.OnListItemClickListener {
    private List<Recipe> mRecipes;
    private Recipe mRecipe;
    private IngredientsFragment mIngredientsFragment;
    private InstructionsFragment mInstructionsFragment;
    private VideoFragment mVideoFragment = null;
    private InstructionFragment mInstructionFragment = null;
    private ActivityRecipeBinding mActivityRecipeBinding;
    private String mVideoUrl;
    private String mThumbnailUrl;
    private boolean mIsDualPaneUi = false;
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRecipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        TextView instructionTitleTextView = mActivityRecipeBinding.tvInstructionsTitle;
        instructionTitleTextView.setTypeface(null, Typeface.BOLD);
        mIngredientsFragment = new IngredientsFragment();
        mInstructionsFragment = new InstructionsFragment();

        if (mActivityRecipeBinding.llRecipeInstructions != null) {
            mIsDualPaneUi = true;

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mInstructionFragment = new InstructionFragment();
            }

        } else {
            mIsDualPaneUi = false;
        }

        initViews();

        mDatabase = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public void onListItemSelected(int position) {
        if (mIsDualPaneUi) {
            mRecipe.setStepIdSelected(position);
            mVideoFragment = new VideoFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.video_player_container, mVideoFragment)
                    .commit();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mInstructionFragment = new InstructionFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.instruction_container, mInstructionFragment)
                        .commit();

            }

            displaySelectedInstruction();

        } else {
            Context context = RecipeActivity.this;
            Class<InstructionActivity> destinationActivity = InstructionActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            mRecipe.setStepIdSelected(position);
            startChildActivityIntent.putExtra(String.valueOf(R.string.app_name_recipe_instructions), mRecipe);
            startActivity(startChildActivityIntent);
        }
    }

    public void onSendToShoppingListClicked(View view) {
        List<Ingredient> ingredients = mRecipe.getIngredients();
        AppExecutors.getInstance().diskIO().execute(() -> {
            mDatabase.shoppingListDao().deleteAllIngredients();
            for (Ingredient ingredient : ingredients) {
                String quantity = ingredient.getQuantity();
                String measurement = ingredient.getMeasurement();
                String name = ingredient.getName();
                ShoppingListEntry shoppingListEntry = new ShoppingListEntry(mRecipe.getId(), quantity, measurement, name);
                mDatabase.shoppingListDao().insertIngredient(shoppingListEntry);
            }
        });

        BakingAppWidgetProvider.sendRefreshBroadcast(getApplicationContext());
    }

    private int getVideoPlayerWidth() {
        return Math.round(getScreenWidth() * getVideoPlayerLayoutRatio());
    }

    private int getScreenWidth() {
        ScreenUtils screenUtils = new ScreenUtils(this);
        return screenUtils.getWidthInPixels();
    }

    private float getVideoPlayerLayoutRatio() {
        float recipePanelWeight = 1.0f;
        float instructionPanelWeight = 1.0f;
        LinearLayout.LayoutParams layoutParams;
        LinearLayout recipePanelLayout = mActivityRecipeBinding.llRecipePanel;
        LinearLayout recipeInstructionsLayout = mActivityRecipeBinding.llRecipeInstructions;

        if (recipePanelLayout != null) {
            layoutParams = (LinearLayout.LayoutParams) recipePanelLayout.getLayoutParams();
            recipePanelWeight = layoutParams.weight;
        }

        if (recipeInstructionsLayout != null) {
            layoutParams = (LinearLayout.LayoutParams) recipeInstructionsLayout.getLayoutParams();
            instructionPanelWeight = layoutParams.weight;
        }

        return instructionPanelWeight / (recipePanelWeight + instructionPanelWeight);
    }

    private void initViews() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(String.valueOf(R.string.app_name_recipe_detail))) {
                mRecipe = (Recipe) intent.getSerializableExtra(String.valueOf(R.string.app_name_recipe_detail));
                displayRecipe();
            } else if (intent.hasExtra(BakingAppWidgetProvider.EXTRA_RECIPE_ID)) {
                int recipeId = intent.getIntExtra(BakingAppWidgetProvider.EXTRA_RECIPE_ID, 0);
                loadRecipeById(recipeId);
            }
        }
    }

    private void displaySelectedInstruction() {
        List<Instruction> instructions = mRecipe.getInstructions();
        Instruction instruction = instructions.get(mRecipe.getStepIdSelected());
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (setupMedia(instruction)) {
            int playerHeight = MediaUtils.getOptimumHeightInPxForVideo(getVideoPlayerWidth());
            mVideoFragment = new VideoFragment();
            mVideoFragment.setVideoUrl(mVideoUrl);
            mVideoFragment.setPlayerHeight(playerHeight);
            fragmentManager.beginTransaction()
                    .replace(R.id.video_player_container, mVideoFragment)
                    .commit();
        }

        if (mInstructionFragment != null) {
            mInstructionFragment.setTerseInstruction(instruction.getShortDescription());
            mInstructionFragment.setVerboseInstruction(instruction.getDescription());
            fragmentManager.beginTransaction()
                    .replace(R.id.instruction_container, mInstructionFragment)
                    .commit();
        }
    }

    private boolean setupMedia(Instruction instruction) {
        String videoUrl = instruction.getVideoUrl();
        String imageUrl = instruction.getThumbnailUrl();

        if (videoUrl != null && !videoUrl.isEmpty()) {
            if (MediaUtils.isVideo(videoUrl)) {
                mVideoUrl = videoUrl;
                return true;
            } else if (MediaUtils.isImage(videoUrl)) {
                mThumbnailUrl = videoUrl;
                return false;
            }
        } else if (imageUrl != null && !imageUrl.isEmpty()) {
            if (MediaUtils.isImage(imageUrl)) {
                mThumbnailUrl = imageUrl;
                return false;
            } else if (MediaUtils.isVideo(imageUrl)) {
                mVideoUrl = imageUrl;
                return true;
            }
        }

        return false;
    }

    private void loadRecipeById(int recipeId) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<List<Recipe>> recipeList =  retrofitApiInterface.getAllRecipes();
        recipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes = response.body();
                mRecipe = getRecipeById(recipeId);
                displayRecipe();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {

            }
        });
    }

    private Recipe getRecipeById(int recipeId) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == recipeId) {
                return recipe;
            }
        }

        return null;
    }

    private void displayRecipe() {
        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            mIngredientsFragment = new IngredientsFragment();
            mIngredientsFragment.setIngredients(mRecipe.getIngredients());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredients_container, mIngredientsFragment)
                    .commit();
            if (mInstructionsFragment != null) {
                mInstructionsFragment.setInstructions(mRecipe.getInstructions());
                fragmentManager.beginTransaction()
                        .replace(R.id.instructions_container, mInstructionsFragment)
                        .commit();
            }
            if (mIsDualPaneUi) {
                displaySelectedInstruction();
            }
        }
    }
}