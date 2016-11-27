package uk.domdudley.mvp_api_test.ui;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.domdudley.mvp_api_test.models.Movie;

/**
 * Created by Dom on 25/11/2016.
 */
public interface MovieView {
    void onMovieLoadedSuccess(Call movie, Response response);
    void onMovieLoadedFailure(Call movie, Throwable t);
}
