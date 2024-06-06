package fr.epf.min2.countries_app.api

import android.content.Context
import android.util.Log
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

private const val TAG = "UpdateSavedData"
class UpdateSavedData {
    /**
     * This method is used to call the "all" API endpoint
     * The result is then saved in the all.json file
     */
    companion object {
        fun updateAllData(context: Context) {
            var success = false
            var lastCountriesFetch : Date = Date(0)
            var cptLimit = 0
            do {
                ApiService.getAll().enqueue(object : Callback<List<Country>> {
                    override fun onResponse(
                        call: Call<List<Country>>,
                        response: Response<List<Country>>
                    ) {
                        if (response.isSuccessful) {
                            success = true
                            lastCountriesFetch = Date()
                            val countries = response.body()
                            if (countries != null) {
                                SavedDataLoader.getInstance()
                                    .saveCountriesToJsonFile(countries, context)
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                        Log.e(TAG, "Error while fetching all countries", t)
                        Log.d(TAG, "Retrying until success")
                        Log.d(TAG, "Last fetch was at $lastCountriesFetch")
                    }
                })
                cptLimit++
            }
            while (!success && cptLimit < 5)
        }
    }
}