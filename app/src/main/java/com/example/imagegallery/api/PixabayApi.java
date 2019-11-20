package com.example.imagegallery.api;

import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


//pixabay endpoints communication interface
public interface PixabayApi {
    String API_key = "14293127-506d9fc7177ece5ba2ada0dcb";
    String base_url = "https://pixabay.com/api/";

    @GET("?key=" + API_key)
    Call<DataList> getDefaultDataList();

    @GET("?key=" + API_key + "&id=")
    Call<Hit> getHitById(@Query("id") String id);

    @GET("?key=" + API_key + "&editors_choice=true")
    Call<DataList> searchImages(@Query("q") String q,
                                @Query("page") int page,
                                @Query("per_page") int per_page);

    /**
     * build retrofit object. using that object, instantiate pixabay endpoint interface and
     * return it.
     *
     * @return PixabayApiImp Interface
     */
    public static PixabayApi getPixabayApi() {
        //if (pixabayApi == null) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PixabayApi.class);
        // }
        // return pixabayApi;
    }
}

