package fr.epf.min2.countries_app.ui.search

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country
import java.text.Normalizer

private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text
    private var countries = MutableLiveData<List<Country>>()
    var filteredCountries = MutableLiveData<List<Country>>()
    var contientFilter : String = ""


    init{
        countries.observeForever {
            val data = countries.value
            if (data != null && contientFilter.isNotEmpty()) {
                val filteredData = data.filter {
                    it.region.contains(contientFilter, ignoreCase = true)
                }
                filteredCountries.postValue(filteredData)
            }
            else data?.let { filteredCountries.postValue(it) }
        }
    }

    /*val countries = liveData(Dispatchers.IO) {
        val repository = CountryRepository(ApiService())
        val data = repository.getAllCountries()
        emit(data)
    }*/
    fun lookUpInputedString(query: String) {
        val data = SavedDataLoader.getInstance().combinedLookup(query)
        countries.postValue(data)
    }
    fun sortAtoZ() {
        val data = countries.value
        if (data != null) {
            Log.d(TAG, "Sorting A to Z")
            val sortedData = data.sortedWith(compareBy {
                Normalizer.normalize(it.name.common, Normalizer.Form.NFD)
                    .replace("\\p{M}".toRegex(), "")
            })
            countries.postValue(sortedData)
        }
    }
    fun sortZtoA() {
        val data = countries.value
        if (data != null) {
            val sortedData = data.sortedWith(compareByDescending {
                Normalizer.normalize(it.name.common, Normalizer.Form.NFD)
                    .replace("\\p{M}".toRegex(), "")
            })
            countries.postValue(sortedData)
        }
    }

    /*fun filterByRegion(region: String) {
        // Remove the countries that are not in the region
        val data = countries.value?.filter { it.region == region }
        countries.postValue(data!!)
    }*/

    fun applyContientFilter(contient: String) {
        Log.d(TAG, "Applying contient filter: $contient")
        contientFilter = contient
        lookUpInputedString("")
        if (contient=="") {
            val data = countries.value?.filter { it.region == contient }
            countries.postValue(data!!)
        }

    }

}