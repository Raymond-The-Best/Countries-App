package fr.epf.min2.countries_app.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.epf.min2.countries_app.api.ApiService
import fr.epf.min2.countries_app.dataManipulation.LocalDataLookup
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.toCountryString
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

private const val TAG = "HomeViewModel"
class HomeViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _countriesByRegion = MutableLiveData<Map<String, List<Country>>>()
    val countriesByRegion: LiveData<Map<String, List<Country>>> = _countriesByRegion

    init {
        SavedDataLoader.getInstance().countryByRegion.observeForever {
            getCountriesByRegion()
        }
    }

    fun getCountriesByRegion() {

        viewModelScope.launch {
            val regions = listOf("Europe", "Americas", "Antartic", "Asia", "Africa", "Oceania")
            val countriesByRegion = mutableMapOf<String, List<Country>>()
            regions.map {region ->
                countriesByRegion[region] = SavedDataLoader.getInstance().lookupByRegion(region) ?: emptyList()
            }

            _countriesByRegion.postValue(countriesByRegion)
            Log.d(TAG, "Countries by region: ${countriesByRegion.mapValues { (_, countries) -> countries.toCountryString() }}")
        }
    }

}