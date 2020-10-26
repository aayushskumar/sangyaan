
package com.covid19.sangyaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Countries extends AppCompatActivity implements CountryAdapter.OnCountryClickListener {

    public static RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        Toolbar toolbar = findViewById(R.id.selectCountryToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setResponse();
    }

    private void setResponse() {
        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()) {

            HttpLoggingInterceptor logger = new HttpLoggingInterceptor((message) ->
                    Log.d("API", message)
            );

            logger.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            Api api = retrofit.create(Api.class);
            Call<List<POJOCountries>> call = api.getCountry();
            call.enqueue(new Callback<List<POJOCountries>>() {
                @Override
                public void onResponse(Call<List<POJOCountries>> call, Response<List<POJOCountries>> response) {
                    if(response.isSuccessful()) {
                        if(response.body() != null){
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                            setAdapter(response);
                        } else {
                            Log.d("Countries", "Response is:\n"+response.body().toString());
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<POJOCountries>> call, Throwable t) {
                    Log.e("Countries onFailure","Throwable:\n"+ Log.getStackTraceString(t));
                }
            });

        }
        else {
            Toast.makeText(this, "Internet not connected", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    setResponse();
                }
            };
            handler.postDelayed(runnable, 3000);
        }

    }

    public void setAdapter(Response<List<POJOCountries>> response) {
        recyclerViewAdapter = new CountryAdapter(this,response.body());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onClickItem(String slug, String country) {
        Intent intent = new Intent(this, CountryData.class);
        intent.putExtra("slug",slug);
        intent.putExtra("country",country);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}