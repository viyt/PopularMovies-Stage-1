package cf.javadev.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cf.javadev.popularmovies.model.Movie;

public class DetailMovieFragment extends Fragment {
    private final static String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String LOGO_SIZE = "w500";
    private static final String KEY_MOVIE = "MOVIE";
    private ImageView logoImage;

    public DetailMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        Movie movie = getActivity().getIntent().getParcelableExtra(KEY_MOVIE);
        TextView title = (TextView) view.findViewById(R.id.title);
        logoImage = (ImageView) view.findViewById(R.id.logo_image_view);
        TextView year = (TextView) view.findViewById(R.id.year);
        TextView rating = (TextView) view.findViewById(R.id.rating);
        TextView description = (TextView) view.findViewById(R.id.description);

        if (movie != null) {
            System.out.println(movie);
            title.setText(movie.getTitle());
            loadPoster(movie.getPosterPath());
            year.setText(String.format("%.4s", movie.getReleaseDate()));
            rating.setText(String.format("%s/10", movie.getVoteAverage()));
            description.setText(movie.getOverview());
        }
        return view;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void loadPoster(String path) {
        String urlBuilder = new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(LOGO_SIZE)
                .append(path).toString();

        Picasso.with(getContext())
                .load(urlBuilder)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.no_image)
                .into(logoImage);
    }
}
