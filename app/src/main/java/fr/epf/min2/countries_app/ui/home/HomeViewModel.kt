package fr.epf.min2.countries_app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.epf.min2.countries_app.api.ApiService
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.toCountryString
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _countriesByRegion = MutableLiveData<Map<String, List<Country>>>()
    val countriesByRegion: LiveData<Map<String, List<Country>>> = _countriesByRegion


    fun getCountriesByRegion() {
        viewModelScope.launch {
            val regions = listOf("Europe", "Americas", "Antartic", "Asia", "Africa", "Oceania")
            val apiService = ApiService()
            val countriesByRegion = mutableMapOf<String, List<Country>>()

            coroutineScope {
                regions.map { region ->
                    async {
                        val call = ApiService.getByRegion(region)
                        call.enqueue(object : Callback<List<Country>> {
                            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                                if (response.isSuccessful) {
                                    countriesByRegion[region] = response.body() ?: emptyList()
                                } else {
                                    Log.d(TAG, "Error while fetching countries by region")
                                }
                            }

                            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                                Log.d(TAG, "Error while fetching countries by region")
                            }
                        })
                    }
                }.forEach { it.await() }
            }

            _countriesByRegion.postValue(countriesByRegion)
            Log.d(TAG, "Countries by region: ${countriesByRegion.mapValues { (_, countries) -> countries.toCountryString() }}")
        }
    }

}