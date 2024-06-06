package fr.epf.min2.countries_app.ui.country

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country


class ActivityItemCountry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_country)

        val countryName = intent.getStringExtra("COUNTRY_NAME")
        val countryDescription = intent.getStringExtra("COUNTRY_DESCRIPTION")
        // Récupérez d'autres informations sur le pays si nécessaire

        // Utilisez les informations du pays pour remplir vos vues
        // Par exemple :
        val textViewName = findViewById<TextView>(R.id.DescNomPays)
        val textViewDescription = findViewById<TextView>(R.id.titreInfos)
        textViewName.text = countryName
        textViewDescription.text = countryDescription
    }
}