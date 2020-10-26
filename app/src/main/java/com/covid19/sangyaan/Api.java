
package com.covid19.sangyaan;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "https://api.covid19api.com/";

    @GET("summary")
    Call<POJOGlobalData> getGlobalData();

    @GET("countries")
    Call<List<POJOCountries>> getCountry();

    @GET("total/country/{country}")
    Call<List<POJOCountryStatus>> getByCountryTotalAllStatus(@Path("country") String country);

}