package fr.epf.min2.countries_app.ui.playlists

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.save.SavedDataLoader

class PlaylistsViewModel: ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This is playlists Fragment"
    }
    val text: LiveData<String> = _text

    fun getPlaylists() {
        
    }
}