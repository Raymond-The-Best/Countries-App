package fr.epf.min2.countries_app.ui.adapter

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
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.item.ActivityItemCountry
import com.bumptech.glide.Glide
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager

private const val TAG = "CountryAdapter"
class CountryAdapter(private val countries: List<Country>, private val listener: CountryPlaylistAdapter.OnDeleteButtonClickListener) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view), CommonCountryAdapter.CountryViewHolder  {
        val countryName: TextView = view.findViewById(R.id.nomPaysVerti)
        val countryRegion : TextView = view.findViewById(R.id.descPaysVerti)
        val countryFlag: ImageView = view.findViewById(R.id.imagePaysVerti)
        override val favoriteButton: ImageButton = view.findViewById(R.id.favVerti)
        override val addButton: ImageButton = view.findViewById(R.id.AddPaysVerti)

        val playlistManager: PlaylistManager = PlaylistManager.getInstance(SharedPrefManager(view.context))


        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && countries != null) {
                    val clickedCountry = countries[position]
                    val intent = Intent(view.context, ActivityItemCountry::class.java)
                    intent.putExtra("COUNTRY_NAME", clickedCountry.name.common)
                    intent.putExtra("COUNTRY_DESCRIPTION", clickedCountry.region)
                    intent.putExtra("COUNTRY_FLAG_URL", clickedCountry.flags.png)
                    intent.putExtra("COUNTRY_REGION", clickedCountry.region)
                    intent.putExtra("COUNTRY_CAPITAL", clickedCountry.capital?.joinToString())
                    intent.putExtra("COUNTRY_SUBREGION", clickedCountry.subregion)
                    intent.putExtra("COUNTRY_INDEPENDENT", clickedCountry.independent)
                    intent.putExtra("COUNTRY_UN_MEMBER", clickedCountry.unMember)
                    intent.putExtra("COUNTRY_CURRENCY", clickedCountry.currencies.values?.joinToString { it.name })
                    intent.putExtra("COUNTRY_LANGUAGE", clickedCountry.languages.values?.joinToString())
                    intent.putExtra("COUNTRY_DEMONYM", clickedCountry.demonyms.values?.joinToString { it.male })
                    intent.putExtra("COUNTRY_POPULATION", clickedCountry.population)
                    intent.putExtra("COUNTRY_DRIVES_ON", clickedCountry.car.side)
                    intent.putExtra("COUNTRY_LAT", clickedCountry.capitalInfo.latlng[0])
                    intent.putExtra("COUNTRY_LNG", clickedCountry.capitalInfo.latlng[1])
                    view.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_verticale_pays, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.name.common
        holder.countryRegion.text = country.region
        Glide.with(holder.itemView.context).load(country.flags.png).into(holder.countryFlag)

        val playlistManager: PlaylistManager = PlaylistManager.getInstance(SharedPrefManager(holder.itemView.context))

        var countryIsFavorite = playlistManager.isCountryFavorite(country.name.common)
        val colorChoice = if (countryIsFavorite) R.color.favorite_color else R.color.black
        holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, colorChoice))

        CommonCountryAdapter.handleFavoriteButton(holder, country, playlistManager, holder.itemView.context)

        holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))

        holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))

        holder.addButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.purple_200))
                    CommonCountryAdapter.addCountryToPlaylist(holder, country, playlistManager, holder.itemView.context)
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
}