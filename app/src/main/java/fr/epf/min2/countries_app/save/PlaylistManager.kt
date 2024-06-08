package fr.epf.min2.countries_app.save

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.save.model.Playlist
import java.util.Date

class PlaylistManager private constructor(private val sharedPrefManager: SharedPrefManager) : ViewModel() {
    private val TAG = "PlaylistManager"
    val playlists = MutableLiveData<MutableList<Playlist>>()

    companion object{
        private var instance : PlaylistManager? = null
        fun getInstance(sharedPrefManager: SharedPrefManager) : PlaylistManager {
            if(instance == null) {
                instance = PlaylistManager(sharedPrefManager)
            }
            return instance!!
        }
    }
    private val localPlaylistsVar : MutableList<Playlist> = mutableListOf()

    fun getAllPlaylists() : List<Playlist>{
        val playlistsIndex: Set<String> = sharedPrefManager.getPlaylistsIndex()
        playlistsIndex.forEach {
            sharedPrefManager.getPlaylist(it)?.let { it1 -> localPlaylistsVar.add(it1) }
        }
        return localPlaylistsVar
    }
    fun getDefaultPlaylists() : List<Playlist> {
        return localPlaylistsVar.filter { it.isDefault }
    }
    fun getCreatedPlaylists() : List<Playlist> {
        return localPlaylistsVar.filter { !it.isDefault }
    }
    fun getPlaylist(name: String) : Playlist? {
        return localPlaylistsVar.find { it.nom == name }
    }
    fun savePlaylist(playlist: Playlist) {
        sharedPrefManager.savePlaylist(playlist)
        // Update the playlists list
        if(playlist.nom !in localPlaylistsVar.map { it.nom }) {
            localPlaylistsVar.add(playlist)
        }
        else {
            localPlaylistsVar.remove(localPlaylistsVar.find { it.nom == playlist.nom })
            localPlaylistsVar.add(playlist)
        }
        playlists.postValue(localPlaylistsVar)
    }
    fun deletePlaylist(playlistName : String) {
        sharedPrefManager.removePlaylist(playlistName)
        localPlaylistsVar.remove(localPlaylistsVar.find { it.nom == playlistName })
        playlists.postValue(localPlaylistsVar)
    }
    fun createDefaultPlaylists(savedDataLoader: SavedDataLoader) {
        val top10Meilleurs = listOf("France", "États-Unis", "Japon", "Allemagne", "Royaume-Uni", "Canada", "Italie", "Australie", "Espagne", "Suisse").map { savedDataLoader.lookupByName(it)!!}
        val plusBellesPlages = listOf("Brésil", "Espagne", "Italie", "Australie", "Grèce", "Thaïlande", "Mexique", "Philippines", "Fidji", "Seychelles").map { savedDataLoader.lookupByName(it)!!}
        val plusBellesMontagnes = listOf("Népal", "Suisse", "Canada", "Pérou", "Nouvelle-Zélande", "Norvège", "Japon", "États-Unis", "France", "Italie").map { savedDataLoader.lookupByName(it)!!}
        val paysPlaylistCreee =  listOf("Japon", "Corée du Sud", "Canada", "États-Unis").map { savedDataLoader.lookupByName(it)!!}
        val defaultPlaylists = listOf(
            Playlist("Favoris", Date(), mutableListOf(), true, false),
            Playlist("Visités", Date(), mutableListOf(), true, false),
            Playlist("À visiter", Date(), mutableListOf(), true, false),
            Playlist("Top 10 des meilleurs pays", Date(), top10Meilleurs.toMutableList(), true, false),
            Playlist("Les plus belles plages", Date(), plusBellesPlages.toMutableList(), true, false),
            Playlist("Les plus belles montagnes", Date(), plusBellesMontagnes.toMutableList(), true, false),
            Playlist("Template playlist", Date(), paysPlaylistCreee.toMutableList(), false, true)
        )
        defaultPlaylists.forEach {
            savePlaylist(it)
        }
        Log.d(TAG, "Default playlists created")

    }

}