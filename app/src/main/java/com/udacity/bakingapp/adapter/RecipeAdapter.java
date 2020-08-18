package com.udacity.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.databinding.RecipeListItemBinding;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Instruction;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private final List<Recipe> mRecipeList;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecipeAdapter(List<Recipe> recipeList, RecyclerView recyclerView, ListItemClickListener onClickListener) {
        mRecipeList = recipeList;
        mOnClickListener = onClickListener;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemViewType(int position) {
        final int VIEW_ITEM = 1;
        final int VIEW_LISTING = 0;

        return mRecipeList.get(position) != null ? VIEW_ITEM : VIEW_LISTING;
    }

    @Override
    public int getItemCount() {
        return (null == mRecipeList) ? 0 : mRecipeList.size();
    }

    public void addRecipeData(List<Recipe> recipes) {
        mRecipeList.addAll(recipes);
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RecipeListItemBinding recipeListItemBinding;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeListItemBinding = DataBindingUtil.bind(itemView);
            if (recipeListItemBinding != null) {
                recipeListItemBinding.cvRecipe.setOnClickListener(this);
            }
        }

        public void bind(int listIndex) {
            if (listIndex <= mRecipeList.size()) {
                Recipe recipe = mRecipeList.get(listIndex);
                displayImage(recipe);
                if (recipeListItemBinding != null) {
                    recipeListItemBinding.tvRecipeName.setText(recipe.getName());
                }
                setCountOfSteps(recipe.getInstructions());
                setCountOfIngredients(recipe.getIngredients());
                setServingSize(recipe.getServingSize());
            }
        }

        private void displayImage(Recipe recipe) {
            String imageUrl = recipe.getImage();

            if (!imageUrl.isEmpty()) {
                Glide
                        .with(itemView)
                        .load(imageUrl)
                        .into(recipeListItemBinding.ivRecipeImage);
           } else {
                recipeListItemBinding.ivRecipeImage.setVisibility(View.GONE);
            }
        }

        private void setServingSize(int servingSize) {
            String text = "Yields " + servingSize + " servings";
            recipeListItemBinding.tvServingSize.setText(text);
        }
        private void setCountOfIngredients(List<Ingredient> ingredients) {
            String text = "Uses " + ingredients.size() + " ingredients.";
            recipeListItemBinding.tvIngredients.setText(text);
        }

        private void setCountOfSteps(List<Instruction> steps) {
            String text = steps.size() + " easy steps.";
            recipeListItemBinding.tvSteps.setText(text);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }
}
