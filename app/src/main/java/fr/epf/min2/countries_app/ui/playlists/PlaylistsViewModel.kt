package fr.epf.min2.countries_app.ui.playlists

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Playlist

class PlaylistsViewModel: ViewModel(){
    var _playlists = MutableLiveData<List<Playlist>>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is playlists Fragment"
    }
    val text: LiveData<String> = _text


    fun getPlaylists(sharedPrefManager : SharedPrefManager) {
        _playlists.postValue(PlaylistManager.getInstance(sharedPrefManager).getCreatedPlaylists())
    }

}