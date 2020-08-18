package com.udacity.bakingapp.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.databinding.FragmentInstructionListItemBinding;
import com.udacity.bakingapp.model.Instruction;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {
    private final List<Instruction> mInstructions;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public InstructionsAdapter(List<Instruction> instructions, ListItemClickListener onClickListener) {
        mInstructions = instructions;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.fragment_instruction_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InstructionViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mInstructions != null) ? mInstructions.size() : 0;
    }

    public class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mShortDescriptionTextView;
        public TextView mVerboseDescriptionTextView;

        public InstructionViewHolder(View itemView) {
            super(itemView);
            FragmentInstructionListItemBinding fragmentInstructionListItemBinding = DataBindingUtil.bind(itemView);
            if (fragmentInstructionListItemBinding != null) {
                mShortDescriptionTextView = fragmentInstructionListItemBinding.tvInstructionShortDescription;
                mVerboseDescriptionTextView = fragmentInstructionListItemBinding.tvInstructionVerboseDescription;
            }
            itemView.setOnClickListener(this);
        }

        public void bind(int listIndex) {
            if (listIndex <= mInstructions.size()) {
                Instruction instruction = mInstructions.get(listIndex);
                String shortDescription = instruction.getShortDescription();
                String verboseDescription = instruction.getDescription();

                if (!shortDescription.isEmpty()) {
                    mShortDescriptionTextView.setText(shortDescription);
                    mShortDescriptionTextView.setTypeface(null, Typeface.BOLD);
                } else {
                    mShortDescriptionTextView.setVisibility(View.GONE);
                }

                if (mVerboseDescriptionTextView != null) {
                    if (!verboseDescription.isEmpty() && !verboseDescription.equalsIgnoreCase(shortDescription)) {
                        mVerboseDescriptionTextView.setText(verboseDescription);
                    } else {
                        mVerboseDescriptionTextView.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }
}