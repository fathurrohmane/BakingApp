package com.elkusnandi.bakingapp.feature.recipe_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.common.FragmentDataListener;
import com.elkusnandi.bakingapp.data.model.CookingStep;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class RecipeDetailFragment extends Fragment {

    public static final String ARG_COOKING_STEP = "cooking_step";
    public static final String ARG_POSITION = "position";
    public static final String ARG_HAS_PREV = "prev";
    public static final String ARG_HAS_NEXT = "next";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREV = "prev";

    @BindView(R.id.exoplayer)
    SimpleExoPlayerView simpleExoPlayerView;

    @Nullable
    @BindView(R.id.textview_description)
    TextView textViewDescription;

    @Nullable
    @BindView(R.id.button_next)
    Button buttonNext;

    @Nullable
    @BindView(R.id.button_prev)
    Button buttonPrev;

    @BindView(R.id.viewanimator_player)
    ViewAnimator viewAnimator;

    private SimpleExoPlayer exoPlayer;
    private MediaSource videoSource;

    private FragmentDataListener listener;
    private CookingStep cookingStep;
    private int position;
    private boolean hasPrev;
    private boolean hasNext;
    private Bundle bundle;

    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(CookingStep cookingStep, int pos, boolean hasPrev, boolean hasNext) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_COOKING_STEP, cookingStep);
        bundle.putInt(ARG_POSITION, pos);
        bundle.putBoolean(ARG_HAS_PREV, hasPrev);
        bundle.putBoolean(ARG_HAS_NEXT, hasNext);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentDataListener) {
            listener = (FragmentDataListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_COOKING_STEP)) {
            cookingStep = getArguments().getParcelable(ARG_COOKING_STEP);
            position = getArguments().getInt(ARG_POSITION);
            hasPrev = getArguments().getBoolean(ARG_HAS_PREV);
            hasNext = getArguments().getBoolean(ARG_HAS_NEXT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeExoplayer(Uri.parse(cookingStep.getVideoUrl()));
        if (textViewDescription != null) {
            textViewDescription.setText(cookingStep.getDescription());
        }
        if ((buttonPrev != null && !hasPrev) || (buttonPrev != null && getResources().getBoolean(R.bool.is_tablet))) {
            buttonPrev.setVisibility(View.INVISIBLE);
        }
        if ((buttonNext != null && !hasNext) || (buttonPrev != null && getResources().getBoolean(R.bool.is_tablet))) {
            buttonNext.setVisibility(View.INVISIBLE);
        }

        if (cookingStep.getVideoUrl().isEmpty()) {
            viewAnimator.setDisplayedChild(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyExoplayer();
    }

    @Optional
    @OnClick({R.id.button_next, R.id.button_prev})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                bundle = new Bundle();
                bundle.putInt("nextPosition", position + 1);
                listener.onDataReceived(ACTION_NEXT, bundle);
                break;
            case R.id.button_prev:
                bundle = new Bundle();
                bundle.putInt("nextPosition", position - 1);
                listener.onDataReceived(ACTION_NEXT, bundle);
                break;
        }
    }

    private void initializeExoplayer(Uri uri) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        simpleExoPlayerView.setPlayer(exoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "BakingApp"), null);
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.
        exoPlayer.prepare(videoSource);

    }

    private void destroyExoplayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
