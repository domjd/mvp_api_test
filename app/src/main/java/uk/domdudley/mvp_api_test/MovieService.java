package uk.domdudley.mvp_api_test;

import android.database.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uk.domdudley.mvp_api_test.models.Movie;

/**
 * Created by Dom on 25/11/2016.
 */
public interface MovieService {
    @GET("/?&plot=short&r=json")
    Call<Movie> getByTitle(@Query("t") String title);
}
