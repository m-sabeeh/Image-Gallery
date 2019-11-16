package com.example.imagegallery.models;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class DataService {
    //for Pixabay
    private static final String API_key = "14293127-506d9fc7177ece5ba2ada0dcb";
    private static final String base_url = "https://pixabay.com/api/";
    private static PixabayEndpointInterface dataService;
    // "/?key=14293127-506d9fc7177ece5ba2ada0dcb&q=yellow+flowers&image_type=photo&pretty=true";

    //https://pixabay.com/api/?key=14293127-506d9fc7177ece5ba2ada0dcbp


    /**
     * build retrofit object. using that object, instantiate pixabay endpoint interface and
     * return it.
     *
     * @return PixabayEndpointInterface Interface
     */
    public static PixabayEndpointInterface getPixabayAPIServiceInstance() {
        if (dataService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            dataService = retrofit.create(PixabayEndpointInterface.class);
        }
        return dataService;
    }

    //pixabay endpoints
    public interface PixabayEndpointInterface {

        @GET("?key=" + API_key)
        Call<DataList> getDefaultDataList();

        @GET("?key=" + API_key + "&id=")
        Call<Hit> getHitById(@Query("id") String id);

        @GET ("?key=" + API_key +"&q=")
        Call<DataList> getSearchedData(@Query("q") String q);
    }

}
