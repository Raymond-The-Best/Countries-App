package fr.epf.min2.countries_app.ui.playlists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Playlist

private const val TAG = "PlaylistsViewModel"
class PlaylistsViewModel: ViewModel(){
    var _playlists = MutableLiveData<List<Playlist>>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is playlists Fragment"
    }
    val text: LiveData<String> = _text

    fun triggerPlaylistsUpdate(sharedPrefManager : SharedPrefManager) {
        val playlistManager = PlaylistManager.getInstance(sharedPrefManager)
        // Create observer method for the playlists livedata obejct of playlistManager
        _playlists.postValue(playlistManager.getCreatedPlaylists())
    }
}