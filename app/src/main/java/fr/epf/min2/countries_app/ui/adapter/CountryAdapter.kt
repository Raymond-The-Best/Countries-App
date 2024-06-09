package fr.epf.min2.countries_app.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.country.ActivityItemCountry
import com.bumptech.glide.Glide

class CountryAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.nomPaysVerti)
        val countryRegion : TextView = view.findViewById(R.id.descPaysVerti)
        val countryFlag: ImageView = view.findViewById(R.id.imagePaysVerti)
        val favoriteButton: ImageButton = view.findViewById(R.id.favVerti)
        val addButton: ImageButton = view.findViewById(R.id.AddPaysVerti)



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

        holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))
        holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black))

        holder.favoriteButton.setOnClickListener {
            holder.favoriteButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.favorite_color))
        }

        holder.addButton.setOnClickListener {
            holder.addButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.teal_700))
        }
    }

    override fun getItemCount() = countries.size
}