package fr.epf.min2.countries_app.save

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.epf.min2.countries_app.save.model.Country
import java.io.InputStreamReader
private const val TAG = "SavedDataLoader"
class SavedDataLoader private constructor(): ViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()
    private val countryByName: MutableLiveData<HashMap<String, Country>> = MutableLiveData(HashMap())
    val countryByRegion: MutableLiveData<HashMap<String, MutableList<Country>>> = MutableLiveData(HashMap())
    /**
     *  This class will be used to load the saved data from the json files contained in the savedData folder
     */
    fun loadCountriesFromJsonFile(context: Context, fileName: String = "all.json") {
        val gson = Gson()
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        val countryListType = object : TypeToken<List<Country>>() {}.type
        var countries : List<Country> = gson.fromJson(reader, countryListType)
        countries = prepareCountriesForUse(countries)
        Log.d(TAG, "Loaded ${countries.size} countries")
    }
    fun saveCountriesToJsonFile(countries : List<Country>, context: Context, fileName: String = "all.json") {
        val gson = Gson()
        val jsonString = gson.toJson(countries)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
        prepareCountriesForUse(countries)
        Log.d(TAG, "Saved ${countries.size} countries to JSON file")
    }

    private fun prepareCountriesForUse(countries: List<Country>) : List<Country>{
        // Creation des hashmaps pour la recherche
        val updatedCountries = createDataMaps(countries)
        countriesLiveData.postValue(updatedCountries)
        return updatedCountries
    }

    companion object {
        private var instance: SavedDataLoader? = null
        fun getInstance(): SavedDataLoader {
            if (instance == null) {
                instance = SavedDataLoader()
            }
            return instance!!
        }
    }
    private fun createDataMaps(countries: List<Country>) : List<Country> {
        for (country in countries) {
            // Changement des noms du pays en français
            country.name.common = country.translations["fra"]?.common ?: country.name.common
            country.name.official = country.translations["fra"]?.official ?: country.name.official

            // Ajout des pays aux hashmaps
            countryByName.value?.put(country.name.common, country)

            if (!countryByRegion.value?.containsKey(country.region)!!) {
                countryByRegion.value?.put(country.region, mutableListOf())
            }
            countryByRegion.value?.get(country.region)?.add(country)
        }
        countryByRegion.postValue(countryByRegion.value)
        return countries
    }

    fun lookupByName(name: String): Country? {
        return countryByName.value?.get(name)
    }
    fun lookupByRegion(region: String): List<Country>? {
        return countryByRegion.value?.get(region)
    }
    fun lookupByNameSubstring(substring: String): List<Country> {
        return countryByName.value?.filterKeys { it.contains(substring, ignoreCase = true) }?.values?.toList() ?: emptyList()
    }
}