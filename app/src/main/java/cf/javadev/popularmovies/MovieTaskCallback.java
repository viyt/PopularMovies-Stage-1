package cf.javadev.popularmovies;

import cf.javadev.popularmovies.model.Movie;

interface MovieTaskCallback {
    void updateAdapter(Movie[] movies);
}
