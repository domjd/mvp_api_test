package uk.domdudley.mvp_api_test.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Response;
import uk.domdudley.mvp_api_test.R;
import uk.domdudley.mvp_api_test.models.Movie;
import uk.domdudley.mvp_api_test.presenters.MoviePresenter;

public class MainActivity extends AppCompatActivity implements MovieView {

    MoviePresenter presenter;

    TextView tvMovieTitle;
    TextView tvMovieReleaseDate;
    TextView tvPlot;
    ImageView ivPoster;
    RatingBar rbIMDBRating;
    RatingBar rbMetacritic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MoviePresenter(this);

        tvMovieTitle = (TextView) findViewById(R.id.movieTitleTxt);
        tvMovieReleaseDate = (TextView)findViewById(R.id.releaseDateTxt);
        tvPlot = (TextView)findViewById(R.id.plotTxt);
        ivPoster = (ImageView)findViewById(R.id.posterImg);
        rbIMDBRating = (RatingBar)findViewById(R.id.imdbRating);
        rbMetacritic = (RatingBar)findViewById(R.id.metaRating);

        presenter.loadMovie("ip man");

    }

    @Override
    public void onMovieLoadedSuccess(Call movie, Response response) {
        Movie m = (Movie)response.body();
        tvMovieTitle.setText(m.getTitle());
        tvMovieReleaseDate.setText(m.getReleased());
        tvPlot.setText(m.getPlot());
        rbIMDBRating.setIsIndicator(true);
        rbIMDBRating.setRating(Float.valueOf(m.getImdbRating()));
        rbMetacritic.isIndicator();

        if(android.text.TextUtils.isDigitsOnly(m.getMetascore()))
            rbMetacritic.setRating(Float.valueOf(m.getMetascore()) / 10f);
        else
            rbMetacritic.setRating(0);

        new PosterBitmap().execute(m.getPoster());

    }

    @Override
    public void onMovieLoadedFailure(Call movie, Throwable t) {
        tvMovieTitle.setText("FAIL");
    }

    public class PosterBitmap extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
           try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
               return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivPoster.setImageBitmap(bitmap);
        }
    }
}
