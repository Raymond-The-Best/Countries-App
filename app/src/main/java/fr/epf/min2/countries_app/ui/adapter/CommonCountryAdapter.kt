package fr.epf.min2.countries_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.model.Country
private const val TAG = "CommonCountryAdapter"
class CommonCountryAdapter {
    companion object {
        fun handleFavoriteButton(holder: CountryViewHolder, country: Country, playlistManager: PlaylistManager, context: Context, extraMethod: (() -> Unit)? =null){

            holder.favoriteButton.setOnClickListener {

                var countryIsFavorite = playlistManager.isCountryFavorite(country.name.common)
                // Switch the status, since the button was clicked
                countryIsFavorite = !countryIsFavorite
                // Change the color of the favorite button
                val colorChoice = if (countryIsFavorite) R.color.favorite_color else R.color.black

                holder.favoriteButton.setColorFilter(ContextCompat.getColor(context, colorChoice))
                if (countryIsFavorite) {
                    Log.d(TAG, "Adding ${country.name.common} to favorites")
                    playlistManager.addCountryToFavorites(country.name.common)
                } else {
                    playlistManager.removeCountryFromFavorites(country.name.common)
                    Log.d(TAG, "Removing ${country.name.common} from favorites")
                }
                extraMethod?.invoke()
            }
        }

        fun addCountryToPlaylist(holder : CommonCountryAdapter.CountryViewHolder, country: Country, playlistManager: PlaylistManager, context: Context) {
            holder.addButton.setOnClickListener {
                // Retrieve all editable playlists
                val editablePlaylists = playlistManager.getEditablePlaylists()

                // Convert the playlists to an array of strings for the dialog
                val playlistNames = editablePlaylists.map { it.nom }.toTypedArray()

                // Create a new AlertDialog Builder
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setTitle("Sélectionner une playlist")

                // Set the items and their click listener
                builder.setItems(playlistNames) { _, which ->
                    // The 'which' argument contains the index position
                    // of the selected item
                    val selectedPlaylist = editablePlaylists[which]
                    playlistManager.addCountryToPlaylist(selectedPlaylist.nom, country.name.common)
                    Toast.makeText(
                        context,
                        "${country.name.common} added to ${selectedPlaylist.nom}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Create and show the AlertDialog
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
    interface CountryViewHolder  {
        val favoriteButton: ImageButton
        val addButton: ImageButton
    }
}