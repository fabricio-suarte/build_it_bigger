package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import com.udacity.jokeshower.JokeShowerActivity;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button jokeButton;
    private ProgressBar progressBar;
    private EndpointAsyncTask task;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        this.jokeButton = (Button) root.findViewById(R.id.button_joke);
        this.progressBar = (ProgressBar) root.findViewById(R.id.progressBar1);
        this.progressBar.setVisibility(View.INVISIBLE);

        this.jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJoke();
            }
        });

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

    //endregion

}
