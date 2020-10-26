
package com.covid19.sangyaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryData extends AppCompatActivity {

    private TextView confirmed, deaths, recovered, active, countryName;
    private ProgressBar progressBar;
    private String slug, country;
    private MaterialCardView card1,card2,card3,card4;
    private String cases1, cases2, cases3, cases4;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            slug = bundle.getString("slug");
            country = bundle.getString("country");
        }

        Toolbar toolbar = findViewById(R.id.toolbarCountryDataActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirmed = findViewById(R.id.confirmed);
        deaths = findViewById(R.id.deaths);
        recovered = findViewById(R.id.recovered);
        active = findViewById(R.id.active);
        countryName = findViewById(R.id.txtVwCountryData);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);
        card3.setVisibility(View.INVISIBLE);
        card4.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        countryName.setText(country);

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
            Call<List<POJOCountryStatus>> call = api.getByCountryTotalAllStatus(slug);
            call.enqueue(new Callback<List<POJOCountryStatus>>() {
                @Override
                public void onResponse(Call<List<POJOCountryStatus>> call, Response<List<POJOCountryStatus>> response) {
                    if(response.isSuccessful()) {
                        if(response.body() != null){
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                                card1.setVisibility(View.VISIBLE);
                                card2.setVisibility(View.VISIBLE);
                                card3.setVisibility(View.VISIBLE);
                                card4.setVisibility(View.VISIBLE);
                            }
                            try {

                                if(response.body().size() > 0) {
                                    cases1 = response.body().get(response.body().size()-1).getConfirmed();
                                    StringBuilder confirmedCases = new StringBuilder();
                                    for(int i = 0; i < cases1.length() ; i++){
                                        if((cases1.length() - i - 1) % 3 == 0 && i != cases1.length()-1){
                                            confirmedCases.append(cases1.charAt(i)).append(",");
                                        }else{
                                            confirmedCases.append(cases1.charAt(i));
                                        }
                                    }
                                    confirmed.setText(confirmedCases);

                                    cases2 = response.body().get(response.body().size()-1).getDeaths();
                                    StringBuilder deathCases = new StringBuilder();
                                    for(int i = 0; i < cases2.length() ; i++){
                                        if((cases2.length() - i - 1) % 3 == 0 && i != cases2.length()-1){
                                            deathCases.append(cases2.charAt(i)).append(",");
                                        }else{
                                            deathCases.append(cases2.charAt(i));
                                        }
                                    }
                                    deaths.setText(deathCases);

                                    cases3 = response.body().get(response.body().size()-1).getRecovered();
                                    StringBuilder recoveredCases = new StringBuilder();
                                    for(int i = 0; i < cases3.length() ; i++){
                                        if((cases3.length() - i - 1) % 3 == 0 && i != cases3.length()-1){
                                            recoveredCases.append(cases3.charAt(i)).append(",");
                                        }else{
                                            recoveredCases.append(cases3.charAt(i));
                                        }
                                    }
                                    recovered.setText(recoveredCases);

                                    cases4 = response.body().get(response.body().size()-1).getActive();
                                    StringBuilder activeCases = new StringBuilder();
                                    for(int i = 0; i < cases4.length() ; i++){
                                        if((cases4.length() - i - 1) % 3 == 0 && i != cases4.length()-1){
                                            activeCases.append(cases4.charAt(i)).append(",");
                                        }else{
                                            activeCases.append(cases4.charAt(i));
                                        }
                                    }
                                    active.setText(activeCases);
                                }
                                else {
                                    confirmed.setText("0");
                                    deaths.setText("0");
                                    recovered.setText("0");
                                    active.setText("0");
                                }

                            }
                            catch (Exception e) {
                                Log.e("MainActivity",Log.getStackTraceString(e));
                            }
                        } else {
                            Log.d("DisplayBuses", "Response is:\n"+response.body().toString());
                        }

                    }
                }
                @Override
                public void onFailure(Call<List<POJOCountryStatus>> call, Throwable t) {
                    Log.e("SearchBuses onFailure","Throwable:\n"+ Log.getStackTraceString(t));
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

}