package fr.epf.min2.countries_app.dataManipulation

import android.content.Context
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country

class LocalDataLookup(private val context: Context){
    private val countries: List<Country> = SavedDataLoader.loadCountriesFromJsonFile(context)
    private val countryByName: HashMap<String, Country> = HashMap()
    private val countryByRegion: HashMap<String, MutableList<Country>> = HashMap()

    init {
        for (country in countries) {
            countryByName[country.name.common] = country

            if (!countryByRegion.containsKey(country.region)) {
                countryByRegion[country.region] = mutableListOf()
            }
            countryByRegion[country.region]?.add(country)
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
}