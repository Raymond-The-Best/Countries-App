package fr.epf.min2.countries_app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text

    /*val countries = liveData(Dispatchers.IO) {
        val repository = CountryRepository(ApiService())
        val data = repository.getAllCountries()
        emit(data)
    }*/
}