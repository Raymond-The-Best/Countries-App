package fr.epf.min2.countries_app.save

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Playlist
import java.util.Date

class PlaylistManager private constructor(private val sharedPrefManager: SharedPrefManager) : ViewModel() {
    private val TAG = "PlaylistManager"

    val playlists = MutableLiveData<MutableList<Playlist>>()

    companion object{
        const val FAVORITES_NAME = "Favoris"
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
        val top10Meilleurs = setOf("France", "États-Unis", "Japon", "Allemagne", "Royaume-Uni", "Canada", "Italie", "Australie", "Espagne", "Suisse").map   { savedDataLoader.lookupByName(it)!!}
        val plusBellesPlages = setOf("Brésil", "Espagne", "Italie", "Australie", "Grèce", "Thaïlande", "Mexique", "Philippines", "Fidji", "Seychelles").map { savedDataLoader.lookupByName(it)!!}
        val plusBellesMontagnes = setOf("Népal", "Suisse", "Canada", "Pérou", "Nouvelle-Zélande", "Norvège", "Japon", "États-Unis", "France", "Italie").map { savedDataLoader.lookupByName(it)!!}
        val paysPlaylistCreee =  setOf("Japon", "Corée du Sud", "Canada", "États-Unis").map { savedDataLoader.lookupByName(it)!!}
        val defaultPlaylists = setOf(
            Playlist(FAVORITES_NAME, Date(), mutableSetOf(), false, false, true,R.drawable.fav_playlist),
            Playlist("Visités", Date(), mutableSetOf(), false, true, true,R.drawable.europe),
            Playlist("À visiter", Date(), mutableSetOf(), false, true, true,R.drawable.europe),
            Playlist("Top 10 des meilleurs pays", Date(), top10Meilleurs.toMutableSet(), true, false, false,R.drawable.europe),
            Playlist("Les plus belles plages", Date(), plusBellesPlages.toMutableSet(), true, false, false,R.drawable.europe),
            Playlist("Les plus belles montagnes", Date(), plusBellesMontagnes.toMutableSet(), true, false, false,R.drawable.europe),
            Playlist("Template playlist", Date(), paysPlaylistCreee.toMutableSet(), false, true, true,R.drawable.europe)

        )
        defaultPlaylists.forEach {
            savePlaylist(it)
        }
        Log.d(TAG, "Default playlists created")

    }
    fun isCountryFavorite(countryName: String) : Boolean {
        val favPlaylist = localPlaylistsVar.find { it.nom == FAVORITES_NAME }
        return favPlaylist?.pays?.find { it.name.common == countryName } != null
    }
    fun addCountryToFavorites(countryName: String) {
        addCountryToPlaylist(FAVORITES_NAME, countryName)
    }
    fun removeCountryFromFavorites(countryName: String) {
        removeCountryFromPlaylist(FAVORITES_NAME, countryName)
    }
    fun addCountryToPlaylist(playlistName: String, countryName: String) {
        val playlist = localPlaylistsVar.find { it.nom == playlistName }
        val country = SavedDataLoader.getInstance().lookupByName(countryName)
        country?.let {
            playlist?.pays?.add(it)
            savePlaylist(playlist!!)
        }
    }
    fun removeCountryFromPlaylist(playlistName: String, countryName: String) {
        val playlist = localPlaylistsVar.find { it.nom == playlistName }
        val country = SavedDataLoader.getInstance().lookupByName(countryName)
        country?.let {
            playlist?.pays?.remove(it)
            savePlaylist(playlist!!)
        }
    }

    fun getEditablePlaylists(): List<Playlist> {
        return localPlaylistsVar.filter { it.isEditable }
    }

    fun createPlaylist(playlistName: String) {
        val newPlaylist = Playlist(playlistName, Date(), mutableSetOf(), false, true, true, R.drawable.europe)
        savePlaylist(newPlaylist)
    }

}