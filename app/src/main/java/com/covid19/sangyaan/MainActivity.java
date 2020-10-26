
package com.covid19.sangyaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView newConfirmed, newDeaths, newRecovered, totalConfirmed, totalDeaths, totalRecovered;
    private ProgressBar progressBar;
    private MaterialCardView card1,card2,card3,card4,card5,card6;
    private String cases1, cases2, cases3, cases4, cases5, cases6;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbar);

        newConfirmed = findViewById(R.id.newConfirmed);
        newDeaths = findViewById(R.id.newDeaths);
        newRecovered = findViewById(R.id.newRecovered);
        totalConfirmed = findViewById(R.id.totalConfirmed);
        totalDeaths = findViewById(R.id.totalDeaths);
        totalRecovered = findViewById(R.id.totalRecovered);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);
        card3.setVisibility(View.INVISIBLE);
        card4.setVisibility(View.INVISIBLE);
        card5.setVisibility(View.INVISIBLE);
        card6.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setResponse();
    }

    private void setResponse() {
        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnectedOrConnecting()) {
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
            Call<POJOGlobalData> call = api.getGlobalData();
            call.enqueue(new Callback<POJOGlobalData>() {
                @Override
                public void onResponse(Call<POJOGlobalData> call, Response<POJOGlobalData> response) {
                    if(response.isSuccessful()) {
                        if(response.body() != null){
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                                card1.setVisibility(View.VISIBLE);
                                card2.setVisibility(View.VISIBLE);
                                card3.setVisibility(View.VISIBLE);
                                card4.setVisibility(View.VISIBLE);
                                card5.setVisibility(View.VISIBLE);
                                card6.setVisibility(View.VISIBLE);
                            }
                            try {

                                cases1 =  String.valueOf(response.body().getGlobal().getNewConfirmed());
                                StringBuilder newConfirmedCases = new StringBuilder();
                                for(int i = 0; i < cases1.length() ; i++){
                                    if((cases1.length() - i - 1) % 3 == 0 && i != cases1.length()-1){
                                        newConfirmedCases.append(cases1.charAt(i)).append(",");
                                    }else{
                                        newConfirmedCases.append(cases1.charAt(i));
                                    }
                                }
                                newConfirmed.setText(newConfirmedCases);

                                cases2 =  String.valueOf(response.body().getGlobal().getNewRecovered());
                                StringBuilder newRecoveredCases = new StringBuilder();
                                for(int i = 0; i < cases2.length() ; i++){
                                    if((cases2.length() - i - 1) % 3 == 0 && i != cases2.length()-1){
                                        newRecoveredCases.append(cases2.charAt(i)).append(",");
                                    }else{
                                        newRecoveredCases.append(cases2.charAt(i));
                                    }
                                }
                                newRecovered.setText(newRecoveredCases);

                                cases3 =  String.valueOf(response.body().getGlobal().getNewDeaths());
                                StringBuilder newDeathCases = new StringBuilder();
                                for(int i = 0; i < cases3.length() ; i++){
                                    if((cases3.length() - i - 1) % 3 == 0 && i != cases3.length()-1){
                                        newDeathCases.append(cases3.charAt(i)).append(",");
                                    }else{
                                        newDeathCases.append(cases3.charAt(i));
                                    }
                                }
                                newDeaths.setText(newDeathCases);

                                cases4 =  String.valueOf(response.body().getGlobal().getTotalConfirmed());
                                StringBuilder totalConfirmedCases = new StringBuilder();
                                for(int i = 0; i < cases4.length() ; i++){
                                    if((cases4.length() - i - 1) % 3 == 0 && i != cases4.length()-1){
                                        totalConfirmedCases.append(cases4.charAt(i)).append(",");
                                    }else{
                                        totalConfirmedCases.append(cases4.charAt(i));
                                    }
                                }
                                totalConfirmed.setText(totalConfirmedCases);

                                cases5 =  String.valueOf(response.body().getGlobal().getTotalRecovered());
                                StringBuilder totalRecoveredCases = new StringBuilder();
                                for(int i = 0; i < cases5.length() ; i++){
                                    if((cases5.length() - i - 1) % 3 == 0 && i != cases5.length()-1){
                                        totalRecoveredCases.append(cases5.charAt(i)).append(",");
                                    }else{
                                        totalRecoveredCases.append(cases5.charAt(i));
                                    }
                                }
                                totalRecovered.setText(totalRecoveredCases);

                                cases6 =  String.valueOf(response.body().getGlobal().getTotalDeaths());
                                StringBuilder totalDeathCases = new StringBuilder();
                                for(int i = 0; i < cases6.length() ; i++){
                                    if((cases6.length() - i - 1) % 3 == 0 && i != cases6.length()-1){
                                        totalDeathCases.append(cases6.charAt(i)).append(",");
                                    }else{
                                        totalDeathCases.append(cases6.charAt(i));
                                    }
                                }
                                totalDeaths.setText(totalDeathCases);

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
                public void onFailure(Call<POJOGlobalData> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.country:
                startActivity(new Intent(this, Countries.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
