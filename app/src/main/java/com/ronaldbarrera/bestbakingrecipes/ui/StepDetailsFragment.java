package com.ronaldbarrera.bestbakingrecipes.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media.session.MediaButtonReceiver;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.StepModel;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailsFragment extends Fragment {

    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private StepModel mStep;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayer mExoPlayer;
    private boolean mTwoPane = false;

    @BindView(R.id.step_detail_title_textview) TextView stepTitle;
    @BindView(R.id.step_description_textview) TextView stepDescription;
    @BindView(R.id.exo_player_view) PlayerView mPlayerView;
    private boolean playWhenReady;
    private long playbackPosition;
    private int currentWindow;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            Gson gson = new Gson();
            String strOjb = savedInstanceState.getString("steps");
            Type list = new TypeToken<StepModel>() {}.getType();
            mStep = gson.fromJson(strOjb,list);
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            currentWindow = savedInstanceState.getInt("currentWindow");
        } else {
            // start video only at start
            playWhenReady = true;
        }

        stepTitle.setText(mStep.getShortDescription());
        stepDescription.setText(mStep.getDescription());

        return rootView;
    }

    public void setIsTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        Log.d(TAG, "onSaveInstanceState called : " + playWhenReady);
        Gson gson = new Gson();
        currentState.putString("steps", gson.toJson(mStep));
        currentState.putBoolean("playWhenReady", playWhenReady);
        currentState.putLong("playbackPosition", playbackPosition);
        currentState.putInt("currentWindow", currentWindow);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart called : " + playWhenReady);
        super.onStart();
        if(mStep.getVideoURL() == null || mStep.getVideoURL().equals("")) {
            mPlayerView.setVisibility(View.GONE);
        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && mTwoPane == false){
                stepTitle.setVisibility(View.INVISIBLE);
                stepDescription.setVisibility(View.INVISIBLE);
            }
            initializeMediaSession();
            initializePlayer();
        }
    }

    private void initializePlayer() {
        // Creating the player
        mExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();

        // Bind the play to the view
        mPlayerView.setPlayer(mExoPlayer);

        Uri uri = Uri.parse(mStep.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);

        Log.d(TAG, "initializePlayer called : " + playWhenReady);
        mExoPlayer.seekTo(currentWindow, playbackPosition);
        mExoPlayer.prepare(mediaSource, false, false);
        mExoPlayer.setPlayWhenReady(playWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null)
            playWhenReady = mExoPlayer.getPlayWhenReady();
    }

    public void setStep(StepModel  step) {
        this.mStep = step;
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {
        public MediaReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
}
