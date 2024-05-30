package fr.epf.min2.countries_app.save

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefManager(context: Context) {

    private val TAG = "SharedPrefManager"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    fun saveSearchQuery(query: String) {

        val editor = sharedPreferences.edit()
        val existingHistory: Set<String> = getSearchHistory()
        val updatedHistory: MutableSet<String> = existingHistory.toMutableSet()
        updatedHistory.add(query)
        editor.putStringSet("history", updatedHistory)
        editor.apply()
        //Log "the search query was added to the history"
        Log.d(TAG, "the search query was added to the history")
    }

    fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()
    }

    fun clearSearchHistory() {
        val editor = sharedPreferences.edit()
        editor.remove("history")
        editor.apply()
    }
}