package fr.epf.min2.countries_app.save

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.Playlist

class SharedPrefManager(context: Context) {

    private val TAG = "SharedPrefManager"

    private val sharedPrefSearch: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val sharedPrefPlaylist: SharedPreferences = context.getSharedPreferences("playlists", Context.MODE_PRIVATE)
    private val gson = Gson()
    private var PLAYLIST_INDEX_NAME = "Playlist_Index"

    // SEARCH QUERY HISTORY METHODS----------------------------------------------
    fun saveSearchQuery(query: String) {

        val editor = sharedPrefSearch.edit()
        val existingHistory: Set<String> = getSearchHistory()
        val updatedHistory: MutableSet<String> = existingHistory.toMutableSet()
        updatedHistory.add(query)
        editor.putStringSet("history", updatedHistory)
        editor.apply()
        //Log "the search query was added to the history"
        Log.d(TAG, "the search query was added to the history")
    }

    fun getSearchHistory(): Set<String> {
        Log.d(TAG, "Content of the search history: ${sharedPrefSearch.getStringSet("history", emptySet())}")
        return sharedPrefSearch.getStringSet("history", emptySet()) ?: emptySet()
    }

    fun clearSearchHistory() {
        val editor = sharedPrefSearch.edit()
        editor.remove("history")
        editor.apply()
    }
    fun removeSearchQuery(query: String) {
        val editor = sharedPrefSearch.edit()
        val existingHistory: Set<String> = getSearchHistory()
        val updatedHistory: MutableSet<String> = existingHistory.toMutableSet()
        updatedHistory.remove(query)
        editor.putStringSet("history", updatedHistory)
        editor.apply()
    }

    // PLAYLISTS METHODS---------------------------------------------------------
    fun savePlaylist(playlist: Playlist) {
        Log.d(TAG, "Saving playlist ${playlist.nom} in SharedPref")
        val editor = sharedPrefPlaylist.edit()
        val json = gson.toJson(playlist)
        editor.putString(playlist.nom, json)
        editor.apply()
        addPlaylistToIndex(playlist.nom)
    }

    fun getPlaylist(name: String): Playlist? {
        val json = sharedPrefPlaylist.getString(name, null)
        json?.let {
            val type = object : TypeToken<Playlist>() {}.type
            return gson.fromJson(it, type)
        }
        return null
    }
    fun addCountryToPlaylist(name: String, country : Country) {
        val playlist = getPlaylist(name)
        playlist?.let {
            playlist.pays.add(country)
            savePlaylist(playlist)
        }
    }
    fun removeCountryFromPlaylist(name: String, country: Country) {
        val playlist = getPlaylist(name)
        playlist?.let {
            playlist.pays.remove(country)
            savePlaylist(playlist)
        }
    }
    fun removePlaylist(name: String) {
        val editor = sharedPrefPlaylist.edit()
        editor.remove(name)
        editor.apply()
        removePlaylistFromIndex(name)
    }
    fun getPlaylistsIndex(): Set<String> {
        return sharedPrefPlaylist.getStringSet(PLAYLIST_INDEX_NAME, emptySet()) ?: emptySet()
    }
    private fun removePlaylistFromIndex(name: String) {
        val editor = sharedPrefPlaylist.edit()
        val existingPlaylists: Set<String> = getPlaylistsIndex()
        val updatedPlaylists: MutableSet<String> = existingPlaylists.toMutableSet()
        updatedPlaylists.remove(name)
        editor.putStringSet(PLAYLIST_INDEX_NAME, updatedPlaylists)
        editor.apply()
    }

    private fun addPlaylistToIndex(name: String) {
        val editor = sharedPrefPlaylist.edit()
        val existingPlaylists: Set<String> = getPlaylistsIndex()
        val updatedPlaylists: MutableSet<String> = existingPlaylists.toMutableSet()
        updatedPlaylists.add(name)
        editor.putStringSet(PLAYLIST_INDEX_NAME, updatedPlaylists)
        editor.apply()
    }
}