package fr.epf.min2.countries_app.api

import android.content.Context
import android.util.Log
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.model.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

private const val TAG = "UpdateSavedData"
class UpdateSavedData {
    /**
     * This method is used to call the "all" API endpoint
     * The result is then saved in the all.json file
     */
    companion object {
        private var lastCountriesFetch : LocalDate = LocalDate.of(1970, 1, 1)
        fun updateAllData(context: Context) {
            var success = false
            var cptLimit = 0
            Log.d(TAG, "Current time: ${LocalDate.now()}")
            Log.d(TAG, "Last fetch was at $lastCountriesFetch")
            // Check if the last fetch was more than a day ago
            if (lastCountriesFetch.plusDays(1).isBefore(LocalDate.now())) {
                Log.d(TAG, "Fetching all countries")
                do {
                    ApiService.getAll().enqueue(object : Callback<List<Country>> {
                        override fun onResponse(
                            call: Call<List<Country>>,
                            response: Response<List<Country>>
                        ) {
                            if (response.isSuccessful) {
                                success = true
                                lastCountriesFetch = LocalDate.now()
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
            else {
                Log.d(TAG, "Last fetch was at $lastCountriesFetch (less than a day ago)")
            }


        }
    }
}