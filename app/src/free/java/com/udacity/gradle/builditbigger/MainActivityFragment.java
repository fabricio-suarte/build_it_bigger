package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.jokeshower.JokeShowerActivity;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd interstitialAd;
    private Button jokeButton;
    private ProgressBar progressBar;
    private EndpointAsyncTask task;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        this.jokeButton = (Button) root.findViewById(R.id.button_joke);
        this.progressBar = (ProgressBar) root.findViewById(R.id.progressBar1);
        this.progressBar.setVisibility(View.INVISIBLE);

        this.jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAd.show();
            }
        });

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //Prepare an interstitialAd
        interstitialAd = new InterstitialAd(this.getActivity());
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJoke();
            }
        });

        requestNewInterstitial();

        return root;
    }

    //region private aux methods

    private void showJoke() {

        progressBar.setVisibility(View.VISIBLE);

        this.task = new EndpointAsyncTask(new Runnable() {
            @Override
            public void run() {
                showJokeActivity();
            }
        });
        task.execute();

    }

    private void showJokeActivity() {
        String result = null;
        try {
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent jokeShowerIntent = new Intent(getActivity(), JokeShowerActivity.class);
        jokeShowerIntent.putExtra(JokeShowerActivity.JOKE_EXTRA_KEY, result);

        getActivity().startActivity(jokeShowerIntent);

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
    }

    //endregion

}
