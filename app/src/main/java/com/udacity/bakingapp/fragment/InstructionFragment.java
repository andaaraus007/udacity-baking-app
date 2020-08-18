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
import com.udacity.bakingapp.databinding.FragmentInstructionBinding;

public class InstructionFragment extends Fragment {
    private TextView mTerseInstructionTextView;
    private TextView mVerboseInstructionTextView;
    private String mTerseInstruction;
    private String mVerboseInstruction;

    public InstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
        setupViews(rootView);

        return rootView;
    }

    public void setTerseInstruction(String instruction) {
        mTerseInstruction = instruction;
    }

    public void setVerboseInstruction(String instruction) {
        mVerboseInstruction = instruction;
    }

    public void hideViews() {
        if (mTerseInstructionTextView != null) {
            mTerseInstructionTextView.setVisibility(View.GONE);
        }

        if (mVerboseInstructionTextView != null) {
            mVerboseInstructionTextView.setVisibility(View.GONE);
        }
    }

    private void setupViews(View rootView) {
        FragmentInstructionBinding fragmentInstructionBinding = DataBindingUtil.bind(rootView);
        if (fragmentInstructionBinding != null) {
            mVerboseInstructionTextView = fragmentInstructionBinding.tvInstructionVerboseDescription;
            mTerseInstructionTextView = fragmentInstructionBinding.tvInstructionShortDescription;
            mTerseInstructionTextView.setTypeface(null, Typeface.BOLD);

            if (mTerseInstruction != null) {
                mTerseInstructionTextView.setText(mTerseInstruction);

                if (!mTerseInstruction.equalsIgnoreCase(mVerboseInstruction)) {
                    mVerboseInstructionTextView.setText(mVerboseInstruction);

                } else {
                    mVerboseInstructionTextView.setText("");
                }
            }
        }
    }
}