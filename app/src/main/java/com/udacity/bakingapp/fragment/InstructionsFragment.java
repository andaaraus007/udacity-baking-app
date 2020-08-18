package com.udacity.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapter.InstructionsAdapter;
import com.udacity.bakingapp.model.Instruction;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class InstructionsFragment extends Fragment implements InstructionsAdapter.ListItemClickListener {
    private List<Instruction> mInstructions;
    private OnListItemClickListener mListItemClickListener;

    @Override
    public void onListItemClick(int clickedItemIndex) {
        mListItemClickListener.onListItemSelected(clickedItemIndex);
    }

    public interface OnListItemClickListener {
        void onListItemSelected(int position);
    }

    public InstructionsFragment() {
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mListItemClickListener = (OnListItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            InstructionsAdapter instructionsAdapter = new InstructionsAdapter(mInstructions, this);
            recyclerView.setAdapter(instructionsAdapter);
        }
        return view;
    }

    public void setInstructions(List<Instruction> instructions) {
        mInstructions = instructions;
    }
}