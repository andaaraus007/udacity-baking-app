package com.udacity.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.databinding.FragmentVideoBinding;

import java.util.Objects;

public class VideoFragment extends Fragment {
    private static final String TAG = "[Debug]" + VideoFragment.class.getSimpleName();
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private static Bundle sSavedInstanceState = null;
    private static int sPlayerHeightInPx = 0;

    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private DefaultTrackSelector  mTrackSelector;
    private DefaultTrackSelector.Parameters mTrackSelectorParameters;
    private MediaSource mMediaSource;
    private String mVideoUrl;
    private boolean mStartAutoPlay;
    private int mStartWindow;
    private long mStartPosition;

    public VideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        FragmentVideoBinding fragmentVideoBinding = DataBindingUtil.bind(rootView);

        if (fragmentVideoBinding != null) {
            mPlayerView = fragmentVideoBinding.playerView;
        }

        setPlayerViewHeight();

        if (savedInstanceState != null) {
            sSavedInstanceState = savedInstanceState;
        }
        
        if (sSavedInstanceState != null) {
            mTrackSelectorParameters = sSavedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            mStartAutoPlay = sSavedInstanceState.getBoolean(KEY_AUTO_PLAY);
            mStartWindow = sSavedInstanceState.getInt(KEY_WINDOW);
            mStartPosition = sSavedInstanceState.getLong(KEY_POSITION);
        } else {
            DefaultTrackSelector.ParametersBuilder builder =
                    new DefaultTrackSelector.ParametersBuilder(/* context= */ Objects.requireNonNull(getContext()));
            mTrackSelectorParameters = builder.build();
            clearStartPosition();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, mTrackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, mStartAutoPlay);
        outState.putInt(KEY_WINDOW, mStartWindow);
        outState.putLong(KEY_POSITION, mStartPosition);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        if (Util.SDK_INT <= 23) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        if (Util.SDK_INT > 23 ) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public void setPlayerHeight(int height) {
        sPlayerHeightInPx = height;
    }

    private void initializePlayer() {
        if (mVideoUrl != null && !mVideoUrl.isEmpty()) {
            if (mPlayer == null) {
                sSavedInstanceState = null;
                TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
                mTrackSelector = new DefaultTrackSelector(Objects.requireNonNull(getContext()), trackSelectionFactory);
                mTrackSelector.setParameters(mTrackSelectorParameters);
                RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
                mPlayer = new SimpleExoPlayer.Builder(getContext(), renderersFactory)
                        .setTrackSelector(mTrackSelector)
                        .build();
                mPlayer.setAudioAttributes(AudioAttributes.DEFAULT, true);
                mPlayerView.setPlayer(mPlayer);
                mPlayerView.requestFocus();
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                        getContext(),
                        Util.getUserAgent(getContext(), String.valueOf(R.string.app_name)),
                        null);
                mMediaSource = new ProgressiveMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mVideoUrl));
                mPlayer.prepare(mMediaSource);
                mPlayer.setPlayWhenReady(mStartAutoPlay);
                boolean haveStartPosition = mStartWindow != C.INDEX_UNSET;
                if (haveStartPosition) {
                    mPlayer.seekTo(mStartWindow, mStartPosition);
                }
            }
        }
    }

    private void setPlayerViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
        layoutParams.height = sPlayerHeightInPx;
        mPlayerView.setLayoutParams(layoutParams);
    }

    private void updateTrackSelectorParameters() {
        if (mTrackSelector != null) {
            mTrackSelectorParameters = mTrackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (mPlayer != null) {
            mStartAutoPlay = mPlayer.getPlayWhenReady();
            mStartWindow = mPlayer.getCurrentWindowIndex();
            mStartPosition = Math.max(0, mPlayer.getContentPosition());
        }
    }

    private void clearStartPosition() {
        mStartAutoPlay = true;
        mStartWindow = C.INDEX_UNSET;
        mStartPosition = C.TIME_UNSET;
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            mPlayer.release();
            mPlayer = null;
            mMediaSource = null;
            mTrackSelector = null;
        }
    }
}