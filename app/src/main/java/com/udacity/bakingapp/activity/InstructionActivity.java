package com.udacity.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.databinding.ActivityInstructionBinding;
import com.udacity.bakingapp.fragment.InstructionFragment;
import com.udacity.bakingapp.fragment.VideoFragment;
import com.udacity.bakingapp.model.Instruction;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.MediaUtils;
import com.udacity.bakingapp.utils.ScreenUtils;

import java.util.List;

public class InstructionActivity extends AppCompatActivity {
    private Recipe mRecipe = null;
    private Instruction mInstruction = null;
    private VideoFragment mVideoFragment = null;
    private InstructionFragment mInstructionFragment = null;
    private ActivityInstructionBinding mActivityInstructionBinding;
    private String mVideoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityInstructionBinding = DataBindingUtil.setContentView(this, R.layout.activity_instruction);
        mVideoFragment = new VideoFragment();
        mInstructionFragment = new InstructionFragment();
        mVideoFragment.setPlayerHeight(getOptimumPlayerHeight());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.video_player_container, mVideoFragment)
                .add(R.id.instruction_container, mInstructionFragment)
                .commit();

        initViews();
    }

    public void onBackButtonClicked(View view) {
        int currentSelectedStep = mRecipe.getStepIdSelected();

        if (currentSelectedStep > 0) {
            mRecipe.setStepIdSelected(--currentSelectedStep);
            mVideoFragment = new VideoFragment();
            mInstructionFragment = new InstructionFragment();

            displaySelectedInstruction();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.video_player_container, mVideoFragment)
                    .replace(R.id.instruction_container, mInstructionFragment)
                    .commit();
        }
    }

    public void onNextButtonClicked(View view) {
        int currentSelectedStep = mRecipe.getStepIdSelected();

        if (++currentSelectedStep < mRecipe.getInstructions().size()) {
            mRecipe.setStepIdSelected(currentSelectedStep);
            mVideoFragment = new VideoFragment();
            mInstructionFragment = new InstructionFragment();

            displaySelectedInstruction();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.video_player_container, mVideoFragment)
                    .replace(R.id.instruction_container, mInstructionFragment)
                    .commit();
        }
   }

    private void initViews() {
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(String.valueOf(R.string.app_name_recipe_instructions))) {
                mRecipe = (Recipe) intent.getSerializableExtra(String.valueOf(R.string.app_name_recipe_instructions));

                if (mRecipe != null) {
                    setTitle(mRecipe.getName());
                    displaySelectedInstruction();
                }
            }
        }
    }

    private void displaySelectedInstruction() {
        List<Instruction> instructions = mRecipe.getInstructions();
        mInstruction = instructions.get(mRecipe.getStepIdSelected());
        hideButtonsIfNeeded(instructions.size());

        if (setupMedia()) {
            mVideoFragment.setVideoUrl(mVideoUrl);
            mVideoFragment.setPlayerHeight(getOptimumPlayerHeight());

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mInstructionFragment.hideViews();
            }
        } else {
            mVideoFragment.setPlayerHeight(0);
        }

        mInstructionFragment.setTerseInstruction(mInstruction.getShortDescription());
        mInstructionFragment.setVerboseInstruction(mInstruction.getDescription());
    }

    private void hideButtonsIfNeeded(int maxSteps) {
        boolean isPortrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        if (isPortrait) {
            if (mRecipe.getStepIdSelected() == 0) {
                mActivityInstructionBinding.ivBackButton.setVisibility(View.GONE);
                mActivityInstructionBinding.ivNextButton.setVisibility(View.VISIBLE);
            } else if (mRecipe.getStepIdSelected() == (maxSteps - 1)) {
                mActivityInstructionBinding.ivBackButton.setVisibility(View.VISIBLE);
                mActivityInstructionBinding.ivNextButton.setVisibility(View.GONE);
            } else {
                mActivityInstructionBinding.ivBackButton.setVisibility(View.VISIBLE);
                mActivityInstructionBinding.ivNextButton.setVisibility(View.VISIBLE);
            }

        } else {
            mActivityInstructionBinding.ivBackButton.setVisibility(View.GONE);
            mActivityInstructionBinding.ivNextButton.setVisibility(View.GONE);
        }
    }

    private int getOptimumPlayerHeight() {
        ScreenUtils screenUtils = new ScreenUtils(this);

        return MediaUtils.getOptimumHeightInPxForVideo(screenUtils.getWidthInPixels());
    }

    private boolean setupMedia() {
        String videoUrl = mInstruction.getVideoUrl();
        String imageUrl = mInstruction.getThumbnailUrl();

        if (videoUrl != null && !videoUrl.isEmpty()) {
            if (MediaUtils.isVideo(videoUrl)) {
                mVideoUrl = videoUrl;
                return true;
             }
        } else if (imageUrl != null && !imageUrl.isEmpty()) {
            if (MediaUtils.isVideo(imageUrl)) {
                mVideoUrl = imageUrl;
                return true;
            }
        }

        return false;
    }
}