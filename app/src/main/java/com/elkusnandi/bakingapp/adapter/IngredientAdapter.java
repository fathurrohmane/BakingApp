package com.elkusnandi.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.common.RecyclerViewListener;
import com.elkusnandi.bakingapp.data.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> mValues;
    private RecyclerViewListener mListener;
    private Context context;

    public IngredientAdapter(Context context, RecyclerViewListener listener) {
        this.context = context;
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<Ingredient> ingredients) {
        mValues.addAll(ingredients);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.textViewQuantitiy.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getMeasurement());
        holder.textViewIngredient.setText(mValues.get(position).getIngredient());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_quantity)
        public TextView textViewQuantitiy;
        @BindView(R.id.textview_ingredient)
        public TextView textViewIngredient;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
