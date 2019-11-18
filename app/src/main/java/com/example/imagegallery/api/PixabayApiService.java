package com.example.imagegallery.api;

import android.util.Log;

import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PixabayApiService {
    //for Pixabay
    private static final String TAG = "PixabayApiService";
    private static final String API_key = "14293127-506d9fc7177ece5ba2ada0dcb";
    private static final String base_url = "https://pixabay.com/api/";
    private static PixabayApi dataService;
    private ResponseStatusListener listener;
    private String q;

    public void searchRepo(String q, final ResponseStatusListener listener) {
        //this.dataService = service;
        this.listener = listener;
        this.q = q;

        Call<DataList> dataListCall = getPixabayApi().serchRepo(q);
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body().getHits());
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed to get data");
                listener.onFailure(t.getMessage() == null ? "Unknown error" : t.getMessage());
            }
        });
    }

    //pixabay endpoints communication interface
    public interface PixabayApi {

        @GET("?key=" + API_key)
        Call<DataList> getDefaultDataList();

        @GET("?key=" + API_key + "&id=")
        Call<Hit> getHitById(@Query("id") String id);

        @GET("?key=" + API_key + "&q=")
        Call<DataList> serchRepo(@Query("q") String q);

        Call<DataList> getNextPage();
    }

    /**
     * build retrofit object. using that object, instantiate pixabay endpoint interface and
     * return it.
     *
     * @return PixabayApiService Interface
     */
    public static PixabayApi getPixabayApi() {
        if (dataService == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            dataService = retrofit.create(PixabayApi.class);
        }
        return dataService;
    }

    public interface ResponseStatusListener {
        void onSuccess(List<Hit> hitList);

        void onFailure(String error);
    }


}
