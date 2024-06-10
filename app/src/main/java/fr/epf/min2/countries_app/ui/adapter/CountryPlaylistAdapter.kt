package fr.epf.min2.countries_app.ui.adapter

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.item.ActivityItemCountry
import com.bumptech.glide.Glide
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Playlist

private const val TAG = "CountryPlaylistAdapter"
class CountryPlaylistAdapter(private val playlistName : String, private val countries: MutableList<Country>, private val listener: OnDeleteButtonClickListener) : RecyclerView.Adapter<CountryPlaylistAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.nompaysplay)
        val countryRegion : TextView = view.findViewById(R.id.continentpaysplay)
        val countryFlag: ImageView = view.findViewById(R.id.imagePaysPlay)
        val favoriteButton: ImageButton = view.findViewById(R.id.favpaysplay)
        val addButton: ImageButton = view.findViewById(R.id.addpaysplay)
        val deleteButton: ImageButton = view.findViewById(R.id.delpaysplay)

        val playlistManager: PlaylistManager = PlaylistManager.getInstance(SharedPrefManager(view.context))
        val playlist: Playlist? = playlistManager.getPlaylist(playlistName)

        fun addCountryToPlaylist(holder : CountryPlaylistAdapter.CountryViewHolder, country: Country) {
            Log.d(TAG, "Add button was FUCKING clicked")
            // Retrieve all editable playlists
            val editablePlaylists = playlistManager.getEditablePlaylists()

            // Convert the playlists to an array of strings for the dialog
            val playlistNames = editablePlaylists.map { it.nom }.toTypedArray()

            // Create a new AlertDialog Builder
            val builder = androidx.appcompat.app.AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Sélectionner une playlist")

            // Set the items and their click listener
            builder.setItems(playlistNames) { _, which ->
                // The 'which' argument contains the index position
                // of the selected item
                val selectedPlaylist = editablePlaylists[which]
                playlistManager.addCountryToPlaylist(selectedPlaylist.nom, country.name.common)
                Toast.makeText(holder.itemView.context, "${country.name.common} added to ${selectedPlaylist.nom}", Toast.LENGTH_SHORT).show()
            }

            // Create and show the AlertDialog
            val dialog = builder.create()
            dialog.show()
        }

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedCountry = countries[position]
                    val intent = Intent(view.context, ActivityItemCountry::class.java)
                    intent.putExtra("COUNTRY_NAME", clickedCountry.name.common)
                    intent.putExtra("COUNTRY_DESCRIPTION", clickedCountry.region)
                    intent.putExtra("COUNTRY_FLAG_URL", clickedCountry.flags.png)
                    intent.putExtra("COUNTRY_REGION", clickedCountry.region)
                    intent.putExtra("COUNTRY_CAPITAL", clickedCountry.capital.joinToString())
                    intent.putExtra("COUNTRY_SUBREGION", clickedCountry.subregion)
                    intent.putExtra("COUNTRY_INDEPENDENT", clickedCountry.independent)
                    intent.putExtra("COUNTRY_UN_MEMBER", clickedCountry.unMember)
                    intent.putExtra("COUNTRY_CURRENCY", clickedCountry.currencies.values.joinToString { it.name })
                    intent.putExtra("COUNTRY_LANGUAGE", clickedCountry.languages.values.joinToString())
                    intent.putExtra("COUNTRY_DEMONYM", clickedCountry.demonyms.values.joinToString { it.male })
                    intent.putExtra("COUNTRY_POPULATION", clickedCountry.population)
                    intent.putExtra("COUNTRY_DRIVES_ON", clickedCountry.car.side)
                    intent.putExtra("COUNTRY_LAT", clickedCountry.capitalInfo.latlng[0])
                    intent.putExtra("COUNTRY_LNG", clickedCountry.capitalInfo.latlng[1])
                    view.context.startActivity(intent)
                }
            }
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder(view.context)
                        .setTitle("Supprimer le pays")
                        .setMessage("Êtes-vous sûr de vouloir supprimer ce pays de la playlist ?")
                        .setPositiveButton("Oui") { _, _ ->
                            // Récupérer le nom du pays
                            val countryToDelete: Country = countries.toMutableList()[position]
                            // Supprimer le pays de la playlist
                            playlistManager.removeCountryFromPlaylist(playlistName, countryToDelete.name.common)
                            countries.toMutableList().removeAt(position)
                            countries.removeAt(position)
                            notifyItemRemoved(position)
                            listener.onDeleteButtonClick()

                        }
                        .setNegativeButton("Non", null)
                        .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_verticale_pays_dans_playlist, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.name.common
        holder.countryRegion.text = country.region
        Glide.with(holder.itemView.context).load(country.flags.png).into(holder.countryFlag)


        val playlistManager: PlaylistManager = PlaylistManager.getInstance(SharedPrefManager(holder.itemView.context))

        var countryIsFavorite = playlistManager.isCountryFavorite(country.name.common)
        var favoriteColorChoice = if(countryIsFavorite) R.color.favorite_color else R.color.black

        holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, favoriteColorChoice))
        holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))

        holder.favoriteButton.setOnClickListener {
            // Change the color of the favorite button
            countryIsFavorite = !countryIsFavorite
            favoriteColorChoice = if(countryIsFavorite) R.color.favorite_color else R.color.black
            holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, favoriteColorChoice))
            if (countryIsFavorite) {
                Log.d(TAG, "Adding ${country.name.common} to favorites")
                playlistManager.addCountryToFavorites(country.name.common)
            } else {
                playlistManager.removeCountryFromFavorites(country.name.common)
                Log.d(TAG, "Removing ${country.name.common} from favorites")
            }
        }
        // Check if the playlist is editable
        if (holder.playlist?.isEditable == true) {
            holder.deleteButton.visibility = View.VISIBLE
        } else {
            holder.deleteButton.visibility = View.GONE
        }

        holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))

        holder.addButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.purple_200))
                    holder.addCountryToPlaylist(holder, country)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))
                    true
                }
                else -> false
            }
        }
    }

    override fun getItemCount() = countries.size

    interface OnDeleteButtonClickListener {
        fun onDeleteButtonClick()
    }
}
