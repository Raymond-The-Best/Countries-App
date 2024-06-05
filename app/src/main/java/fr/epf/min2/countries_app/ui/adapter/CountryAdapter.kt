package fr.epf.min2.countries_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country

class CountryAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.nomPaysVerti)
        // Add other views as needed...
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_verticale_pays, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.name
        // Set other views as needed...
    }

    override fun getItemCount() = countries.size
}