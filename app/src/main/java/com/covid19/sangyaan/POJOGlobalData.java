
package com.covid19.sangyaan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class POJOGlobalData {

    private Global Global;
    private List<Countries> Countries;
    @SerializedName("Date")
    private String Date;

    public POJOGlobalData(POJOGlobalData.Global global, List<POJOGlobalData.Countries> countries, String date) {
        Global = global;
        Countries = countries;
        Date = date;
    }

    public Global getGlobal(){
        return this.Global;
    }

    public List<Countries> getCountries(){
        return this.Countries;
    }

    public String getDate(){
        return this.Date;
    }

    public class Global {

        @SerializedName("NewConfirmed")
        private int newConfirmed;
        @SerializedName("TotalConfirmed")
        private int totalConfirmed;
        @SerializedName("NewDeaths")
        private int newDeaths;
        @SerializedName("TotalDeaths")
        private int totalDeaths;
        @SerializedName("NewRecovered")
        private int newRecovered;
        @SerializedName("TotalRecovered")
        private int totalRecovered;

        public Global(int newConfirmed, int totalConfirmed, int newDeaths, int totalDeaths, int newRecovered, int totalRecovered) {
            this.newConfirmed = newConfirmed;
            this.totalConfirmed = totalConfirmed;
            this.newDeaths = newDeaths;
            this.totalDeaths = totalDeaths;
            this.newRecovered = newRecovered;
            this.totalRecovered = totalRecovered;
        }

        public int getNewConfirmed() {
            return this.newConfirmed;
        }

        public int getTotalConfirmed() {
            return totalConfirmed;
        }

        public int getNewDeaths() {
            return newDeaths;
        }

        public int getTotalDeaths() {
            return totalDeaths;
        }

        public int getNewRecovered() {
            return newRecovered;
        }

        public int getTotalRecovered() {
            return totalRecovered;
        }

    }

    public class Countries {

        @SerializedName("Country")
        @Expose
        private String country;
        @SerializedName("CountryCode")
        @Expose
        private String countryCode;
        @SerializedName("Slug")
        @Expose
        private String slug;
        @SerializedName("NewConfirmed")
        @Expose
        private int newConfirmed;
        @SerializedName("TotalConfirmed")
        @Expose
        private int totalConfirmed;
        @SerializedName("NewDeaths")
        @Expose
        private int newDeaths;
        @SerializedName("TotalDeaths")
        @Expose
        private int totalDeaths;
        @SerializedName("NewRecovered")
        @Expose
        private int newRecovered;
        @SerializedName("TotalRecovered")
        @Expose
        private int totalRecovered;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Premium")
        @Expose
        private Premium premium;

        public String getCountry(){
            return this.country;
        }

        public String getCountryCode(){
            return this.countryCode;
        }

        public String getSlug(){
            return this.slug;
        }

        public int getNewConfirmed(){
            return this.newConfirmed;
        }

        public int getTotalConfirmed(){
            return this.totalConfirmed;
        }

        public int getNewDeaths(){
            return this.newDeaths;
        }

        public int getTotalDeaths(){
            return this.totalDeaths;
        }

        public int getNewRecovered(){
            return this.newRecovered;
        }

        public int getTotalRecovered(){
            return this.totalRecovered;
        }

        public String getDate(){
            return this.date;
        }

        public class Premium {}

    }

}