package fr.epf.min2.countries_app.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.model.Country

class CommonCountryAdapter {
    fun handleFavoriteButton(holder : CountryViewHolder, country : Country, playlistManager: PlaylistManager, countryIsFavorite : Boolean) : Pair<Boolean, Int>{
        var colorChoice : Int = 0
        holder.favoriteButton.setOnClickListener {
            // Change the color of the favorite button
            colorChoice = if(countryIsFavorite) R.color.favorite_color else R.color.black
            holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, colorChoice))
            if (countryIsFavorite) {
                //Log.d(TAG, "Adding ${country.name.common} to favorites")
                playlistManager.addCountryToFavorites(country.name.common)
            } else {
                playlistManager.removeCountryFromFavorites(country.name.common)
                //Log.d(TAG, "Removing ${country.name.common} from favorites")
            }
        }
        return Pair(!countryIsFavorite, colorChoice)
    }
    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favoriteButton: ImageButton = view.findViewById(R.id.favVerti)
    }
}