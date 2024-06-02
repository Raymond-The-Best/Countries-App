package fr.epf.min2.countries_app.api

import retrofit2.Call
import retrofit2.http.GET
import fr.epf.min2.countries_app.save.model.Country

interface ApiClient {
    @GET("all")
    fun getAllCountries(): Call<List<Country>>
}