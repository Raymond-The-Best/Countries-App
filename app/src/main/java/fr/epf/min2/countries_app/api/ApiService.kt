package fr.epf.min2.countries_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import fr.epf.min2.countries_app.api.ApiClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApiService {

    companion object {
        private val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES) // set the connection timeout
            .readTimeout(10, TimeUnit.MINUTES) // set the read timeout
            .writeTimeout(10, TimeUnit.MINUTES) // set the write timeout
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiClient by lazy { retrofit!!.create(ApiClient::class.java) }

        fun getByRegion(region: String) = service.getCountriesByRegion(region)
        fun getByName(name: String) = service.getCountryByName(name)
        fun getAll() = service.getAllCountries()

    }


}