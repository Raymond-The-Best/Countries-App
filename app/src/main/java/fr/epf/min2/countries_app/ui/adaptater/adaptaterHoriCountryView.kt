package fr.epf.min2.countries_app.ui.adaptater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adaptaterHoriCountryView(private val dataList: List<paysSelonContinent>) : RecyclerView.Adapter<adaptaterHoriCountryView.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagePaysHori)
        val textView: TextView = itemView.findViewById(R.id.nomPaysHori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_horizontale_pays, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.imageView.setImageResource(currentItem.imageResId) // Assurez-vous d'utiliser des images appropriées
        holder.textView.text = currentItem.text
    }

    override fun getItemCount() = dataList.size
}

//data class paysSelonContinent(val imageResId: Int, val text: String)

