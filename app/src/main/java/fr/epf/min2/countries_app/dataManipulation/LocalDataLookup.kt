package fr.epf.min2.countries_app.dataManipulation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country
import java.util.Random

class LocalDataLookup private constructor (private val lifecycleOwner: LifecycleOwner, savedDataLoader: SavedDataLoader){
    private val TAG = "LocalDataLookup"
    private val countries: MutableLiveData<List<Country>> = MutableLiveData(emptyList())
    private val countryByName: HashMap<String, Country> = HashMap()
    private val countryByRegion: HashMap<String, MutableList<Country>> = HashMap()
    private val id = Random().nextInt()
    init {

        //https://developer.android.com/topic/libraries/architecture/livedata?hl=fr#transform_livedata
        Log.d(TAG, "LocalDataLookup created with id $id!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        Log.d(TAG, "LifeCycle's current state is ${lifecycleOwner.lifecycle.currentState}")
        savedDataLoader.countriesLiveData.observe(lifecycleOwner, Observer { countries ->
            Log.d(TAG, "Loaded ${countries.size} countries")
            this.countries.value = countries
            for (country in countries) {
                // Changement des noms du pays en français
                country.name.common = country.translations["fra"]?.common ?: country.name.common
                country.name.official = country.translations["fra"]?.official ?: country.name.official

                // Ajout des pays aux hashmaps
                countryByName[country.name.common] = country

                if (!countryByRegion.containsKey(country.region)) {
                    countryByRegion[country.region] = mutableListOf()
                }
                countryByRegion[country.region]?.add(country)
            }
        })
    }
    companion object{
        private var instance: LocalDataLookup? = null
        fun getInstance(lifecycleOwner: LifecycleOwner, savedDataLoader: SavedDataLoader): LocalDataLookup {
            if (instance == null) {
                instance = LocalDataLookup(lifecycleOwner, savedDataLoader)
            }
            return instance!!
        }
    }

    fun lookupByName(name: String): Country? {
        return countryByName[name]
    }

    fun lookupByRegion(region: String): List<Country>? {
        return countryByRegion[region]
    }

    fun lookupByNameSubstring(substring: String): List<Country> {
        return countryByName.filterKeys { it.contains(substring, ignoreCase = true) }.values.toList()
    }
    override fun toString(): String {
        return "LocalDataLookup ${id}"
    }
}