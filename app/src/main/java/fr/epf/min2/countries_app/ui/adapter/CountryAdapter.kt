package fr.epf.min2.countries_app.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.country.ActivityItemCountry

class CountryAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.nomPaysVerti)
        // Add other views as needed...

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedCountry = countries[position]
                    val intent = Intent(view.context, ActivityItemCountry::class.java)
                    intent.putExtra("COUNTRY_NAME", clickedCountry.name)
                    intent.putExtra("COUNTRY_DESCRIPTION", clickedCountry.region)
                    intent.putExtra("COUNTRY_FLAG", clickedCountry.flags.png)
                    // Ajoutez d'autres informations sur le pays si nécessaire
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
        // Set other views as needed...
    }

    override fun getItemCount() = countries.size
}