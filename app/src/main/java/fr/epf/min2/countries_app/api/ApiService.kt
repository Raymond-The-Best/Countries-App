package fr.epf.min2.countries_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import fr.epf.min2.countries_app.api.ApiClient
class ApiService {

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://your-api-url.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiClient by lazy { retrofit!!.create(ApiClient::class.java) }

        fun getByRegion(region: String) = service.getCountriesByRegion(region)
        fun getByName(name: String) = service.getCountryByName(name)
        fun getAll() = service.getAllCountries()

    }


}