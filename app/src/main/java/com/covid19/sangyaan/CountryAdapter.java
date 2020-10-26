
package com.covid19.sangyaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<POJOCountries> countriesList;
    private OnCountryClickListener clickListener;
    private Context context;

    public CountryAdapter(Context context, List<POJOCountries> countriesList) {
        this.countriesList = countriesList;
        this.context = context;
        try {
            this.clickListener = ((OnCountryClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnCountryClickListener.");
        }
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        if(countriesList!=null) {
            Collections.sort(countriesList, (pojoCountries, t1) -> pojoCountries.getCountry().compareTo(t1.getCountry()));
            holder.country.setText(countriesList.get(position).getCountry());


            holder.clickableLayout.setOnClickListener((View view) -> {
                if(clickListener != null) {
                    clickListener.onClickItem(countriesList.get(position).getSlug(),countriesList.get(position).getCountry());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView country;
        private RelativeLayout clickableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            clickableLayout = itemView.findViewById(R.id.clickableLayout);
        }
    }

    protected interface OnCountryClickListener {
        void onClickItem(String slug, String country);
    }

}