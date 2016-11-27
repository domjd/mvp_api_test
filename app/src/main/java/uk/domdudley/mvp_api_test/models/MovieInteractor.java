package uk.domdudley.mvp_api_test.models;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Adapter;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.domdudley.mvp_api_test.MovieService;
import uk.domdudley.mvp_api_test.presenters.MovieInteractorFinshedListener;

/**
 * Created by Dom on 25/11/2016.
 */
public class MovieInteractor implements Callback<Movie> {

    private MovieInteractorFinshedListener listener;

    public MovieInteractor(MovieInteractorFinshedListener listener) {
        this.listener = listener;
    }

    private Retrofit retrofit(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return rf;
    }

    public void loadMovie(String title){
        MovieService movieService = retrofit().create(MovieService.class);
        Call<Movie> movie = movieService.getByTitle(title);
        movie.enqueue(this);
    }

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        Log.v("STATUS:", " SUCCESS");
        listener.onNetworkSuccess(call,response);
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Log.v("STATUS:", " FAIL");
        listener.onNetworkFailure(call,t);
    }
}
