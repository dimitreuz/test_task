package com.sokolov.dimitreuz.mostdeliciousomelet.model.api;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class OmeletsAPI {

    private final ApiService mApiService;

    private OmeletsAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }


    public ApiService getApiService() {
        return mApiService;
    }

    private static OmeletsAPI mInstance;

    public static OmeletsAPI getInstance() {
        if (mInstance == null) {
            mInstance = new OmeletsAPI();
        }
        return mInstance;
    }

    public interface ApiService {

        String DOMAIN = "http://www.recipepuppy.com/";
        String API_END_POINT = "api/";
        String DEFAULT_URL = API_END_POINT + "?i=onions,garlic&q=omelet&p=3";
        String SEARCH_URL = API_END_POINT;

        @GET(DEFAULT_URL)
        Call<OmeletsAPIHolder> getAllOmelet();

        @GET(SEARCH_URL)
        Call<OmeletsAPIHolder> getSeacrhedOmeletes();
    }
}
