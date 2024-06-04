package fr.epf.min2.countries_app.save

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.epf.min2.countries_app.save.model.Country
import java.io.InputStreamReader

class SavedDataLoader {
    /**
     *  This class will be used to load the saved data from the json files contained in the savedData folder
     */
    companion object{
        fun loadCountriesFromJsonFile(context: Context, fileName: String = "all.json"): List<Country> {
            val gson = Gson()
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val countryListType = object : TypeToken<List<Country>>() {}.type
            return gson.fromJson(reader, countryListType)
        }
    }

}