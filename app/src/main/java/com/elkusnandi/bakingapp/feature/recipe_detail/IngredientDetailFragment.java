package com.elkusnandi.bakingapp.feature.recipe_detail;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.adapter.IngredientAdapter;
import com.elkusnandi.bakingapp.common.FragmentDataListener;
import com.elkusnandi.bakingapp.common.RecyclerViewListener;
import com.elkusnandi.bakingapp.data.Ingredient;

import java.util.ArrayList;

public class IngredientDetailFragment extends Fragment implements RecyclerViewListener {

    private static final String ARG_INGREDIENTS = "ingeredients";

    private int mColumnCount = 2;
    private ArrayList<Ingredient> ingredients;
    private IngredientAdapter adapter;
    private FragmentDataListener mListener;

    public IngredientDetailFragment() {
    }

    public static IngredientDetailFragment newInstance(ArrayList<? extends Parcelable> ingredients) {
        IngredientDetailFragment fragment = new IngredientDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = new ArrayList<>();
            if (getArguments().containsKey(ARG_INGREDIENTS)) {
                ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        if (getResources().getBoolean(R.bool.is_tablet)
                && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mColumnCount = 4;
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            adapter = new IngredientAdapter(getContext(), this);
            recyclerView.setAdapter(adapter);
            adapter.setData(ingredients);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecyclerViewItemClicked(View view, Bundle bundle) {

    }
}
