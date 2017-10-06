package com.elkusnandi.bakingapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.common.RecyclerViewListener;
import com.elkusnandi.bakingapp.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taruna 98 on 29/09/2017.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private Context mContext;
    private List<Recipe> mList;
    private RecyclerViewListener mListener;

    public RecipeAdapter(Context context, RecyclerViewListener onItemClickListener) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        this.mListener = onItemClickListener;
    }

    public void setData(ArrayList<Recipe> recipeList) {
        mList = recipeList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe item = mList.get(position);

        holder.setTextViewId(mContext.getString(R.string.item_recipe_id, item.getId()));
        holder.setTextViewServing(mContext.getString(R.string.item_recipe_serving, item.getServing()));
        holder.setTextViewTitle(mContext.getString(R.string.item_recipe_title, item.getTitle()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", item);
                mListener.onRecyclerViewItemClicked(v, bundle);
            }
        });
        Glide.with(mContext)
                .load(item.getImage())
                .fitCenter()
                .error(R.drawable.ic_photo)
                .into(holder.getImageViewRecipe());

    }

    public ArrayList<Recipe> getRecipes() {
        return (ArrayList<Recipe>) mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_id)
        TextView textViewId;
        @BindView(R.id.textview_serving)
        TextView textViewServing;
        @BindView(R.id.textview_title)
        TextView textViewTitle;
        @BindView(R.id.imageview_recipe)
        ImageView imageViewRecipe;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTextViewId(String textViewId) {
            this.textViewId.setText(textViewId);
        }

        public void setTextViewServing(String textViewServing) {
            this.textViewServing.setText(textViewServing);
        }

        public void setTextViewTitle(String textViewTitle) {
            this.textViewTitle.setText(textViewTitle);
        }

        public ImageView getImageViewRecipe() {
            return imageViewRecipe;
        }
    }
}