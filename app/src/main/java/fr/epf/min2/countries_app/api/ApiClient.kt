package fr.epf.min2.countries_app.api

import retrofit2.Call
import retrofit2.http.GET
import fr.epf.min2.countries_app.save.model.Country
import retrofit2.http.Path

interface ApiClient {
    @GET("all")
    fun getAllCountries(): Call<List<Country>>

    @GET("name/{name}")
    fun getCountryByName(@Path("name") name: String): Call<List<Country>>

    @GET("region/{region}")
    fun getCountriesByRegion(@Path("region") region: String): Call<List<Country>>


}