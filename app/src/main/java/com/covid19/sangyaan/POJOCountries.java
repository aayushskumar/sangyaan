
package com.covid19.sangyaan;

import com.google.gson.annotations.SerializedName;

public class POJOCountries {

    @SerializedName("Country")
    private String Country;
    @SerializedName("Slug")
    private String Slug;
    @SerializedName("ISO2")
    private String ISO2;

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getSlug ()
    {
        return Slug;
    }

    public void setSlug (String Slug)
    {
        this.Slug = Slug;
    }

    public String getISO2 ()
    {
        return ISO2;
    }

    public void setISO2 (String ISO2)
    {
        this.ISO2 = ISO2;
    }

}