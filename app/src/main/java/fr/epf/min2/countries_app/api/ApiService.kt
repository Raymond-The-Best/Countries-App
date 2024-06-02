package fr.epf.min2.countries_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import fr.epf.min2.countries_app.api.ApiClient
class ApiService {

    // Create a Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.eu/rest/v2/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an implementation of the ApiClient interface
    val apiClient: ApiClient = retrofit.create(ApiClient::class.java)

    
}