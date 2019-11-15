package com.example.imagegallery.models;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class DataAPI {
    public static final String key = "14293127-506d9fc7177ece5ba2ada0dcb";
    public static final String url = "https://pixabay.com/api/";
    // "/?key=14293127-506d9fc7177ece5ba2ada0dcb&q=yellow+flowers&image_type=photo&pretty=true";

    //https://pixabay.com/api/?key=14293127-506d9fc7177ece5ba2ada0dcb&id=3063284
    public static DataService dataService;

    public static DataService getDataService() {
        if (dataService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            dataService = retrofit.create(DataService.class);
        }

        return dataService;
    }

    ;


    public interface DataService {

        @GET("?key=" + key)
        Call<DataList> getDataList();

        @GET("?key=" + key + "&{id}=")
        Call<Hit> getHitById(@Path("id") String id);
    }

}
