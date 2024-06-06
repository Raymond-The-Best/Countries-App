package fr.epf.min2.countries_app.save

import android.content.Context
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.Playlist
import java.util.Date

class PlaylistManager private constructor(private val sharedPrefManager: SharedPrefManager) {
    private val TAG = "PlaylistManager"

    companion object{
        private var instance : PlaylistManager? = null
        fun getInstance(sharedPrefManager: SharedPrefManager) : PlaylistManager {
            if(instance == null) {
                instance = PlaylistManager(sharedPrefManager)
            }
            return instance!!
        }
    }
    private val playlists : MutableList<Playlist> = mutableListOf()

    fun getAllPlaylists() : List<Playlist>{
        val playlistsIndex: Set<String> = sharedPrefManager.getPlaylistsIndex()
        playlistsIndex.forEach {
            sharedPrefManager.getPlaylist(it)?.let { it1 -> playlists.add(it1) }
        }
        return playlists
    }
    fun getDefaultPlaylists() : List<Playlist> {
        return playlists.filter { it.isDefault }
    }
    fun getCreatedPlaylists() : List<Playlist> {
        return playlists.filter { !it.isDefault }
    }
    fun getPlaylist(name: String) : Playlist? {
        return playlists.find { it.nom == name }
    }
    fun savePlaylist(playlist: Playlist) {
        sharedPrefManager.savePlaylist(playlist)
        // Update the playlists list
        if(playlist.nom !in playlists.map { it.nom }) {
            playlists.add(playlist)
        }
        else {
            playlists.remove(playlists.find { it.nom == playlist.nom })
            playlists.add(playlist)
        }
    }
    fun deletePlaylist(playlistName : String) {
        sharedPrefManager.removePlaylist(playlistName)
        playlists.remove(playlists.find { it.nom == playlistName })
    }
    fun createDefaultPlaylists(savedDataLoader: SavedDataLoader) {
        val top10Meilleurs = listOf("France", "États-Unis", "Japon", "Allemagne", "Royaume-Uni", "Canada", "Italie", "Australie", "Espagne", "Suisse").map { savedDataLoader.lookupByName(it)!!}
        val plusBellesPlages = listOf("Brésil", "Espagne", "Italie", "Australie", "Grèce", "Thaïlande", "Mexique", "Philippines", "Fidji", "Seychelles").map { savedDataLoader.lookupByName(it)!!}
        val plusBellesMontagnes = listOf("Népal", "Suisse", "Canada", "Pérou", "Nouvelle-Zélande", "Norvège", "Japon", "États-Unis", "France", "Italie").map { savedDataLoader.lookupByName(it)!!}
        val defaultPlaylists = listOf(
            Playlist("Favoris", Date(), mutableListOf(), true, false),
            Playlist("Visités", Date(), mutableListOf(), true, false),
            Playlist("À visiter", Date(), mutableListOf(), true, false),
            Playlist("Top 10 des meilleurs pays", Date(), top10Meilleurs.toMutableList(), true, false),
            Playlist("Les plus belles plages", Date(), plusBellesPlages.toMutableList(), true, false),
            Playlist("Les plus belles montagnes", Date(), plusBellesMontagnes.toMutableList(), true, false)
        )
        defaultPlaylists.forEach {
            savePlaylist(it)
        }
    }

}