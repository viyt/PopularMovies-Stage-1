package cf.javadev.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cf.javadev.popularmovies.model.Movie;

class MovieAdapter extends BaseAdapter {
    private final static String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String IMAGE_SIZE = "w500";
    private final Context context;
    private final List<Movie> movies;

    MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ImageView imageView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView = (ImageView) inflater.inflate(R.layout.item_grid_movie, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(IMAGE_SIZE)
                .append(movie.getPosterPath().trim()).toString();

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.no_image)
                .into(imageView);
        return imageView;
    }

    void clear() {
        if (movies.size() > 0) {
            movies.clear();
        }
    }
}
