package cf.javadev.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;

import cf.javadev.popularmovies.model.Movie;

public class MovieFragment extends Fragment {
    private static final String KEY_MOVIE = "MOVIE";
    private static final String KEY_MOVIE_LIST = "MOVIE_LIST";
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;

    public MovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_MOVIE_LIST)) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList(KEY_MOVIE_LIST);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(KEY_MOVIE, movie);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    private void updateMovie() {
        if (isOnline()) {
            LoadMoviesTask moviesTask = new LoadMoviesTask(new MovieTaskCallback() {
                @Override
                public void updateAdapter(Movie[] movies) {
                    if (movies != null) {
                        movieAdapter.clear();
                        Collections.addAll(movieList, movies);
                        movieAdapter.notifyDataSetChanged();
                    }
                }
            });
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortingOrder = preferences.getString(getString(R.string.pref_sort_key),
                    getString(R.string.pref_sort_popular_value));
            moviesTask.execute(sortingOrder);
        } else {
            MessageUtil.showLongErrorMessage(getActivity(),
                    getString(R.string.msg_err_no_internet));
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST, movieList);
        super.onSaveInstanceState(outState);
    }
}
