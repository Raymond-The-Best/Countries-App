package fr.epf.min2.countries_app.ui.country

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.epf.min2.countries_app.R

class ActivityItemCountry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_country)

        val countryName = intent.getStringExtra("COUNTRY_NAME")
        val countryDescription = intent.getStringExtra("COUNTRY_DESCRIPTION")
        // Récupérez d'autres informations sur le pays si nécessaire
        val countryRegion = intent.getStringExtra("COUNTRY_REGION")
        val countryCapital = intent.getStringExtra("COUNTRY_CAPITAL")
        val countrySubregion = intent.getStringExtra("COUNTRY_SUBREGION")
        val countryIndependent = intent.getBooleanExtra("COUNTRY_INDEPENDENT", false)
        val countryUnMember = intent.getBooleanExtra("COUNTRY_UN_MEMBER", false)
        val countryCurrency = intent.getStringExtra("COUNTRY_CURRENCY")
        val countryLanguage = intent.getStringExtra("COUNTRY_LANGUAGE")
        val countryDemonym = intent.getStringExtra("COUNTRY_DEMONYM")
        val countryPopulation = intent.getIntExtra("COUNTRY_POPULATION", 0)
        val countryDrivesOn = intent.getStringExtra("COUNTRY_DRIVES_ON")

        // Utilisez les informations du pays pour remplir vos vues
        val textViewName = findViewById<TextView>(R.id.NomPays)
        val textViewDescription = findViewById<TextView>(R.id.titreInfos)
        val textViewRegion = findViewById<TextView>(R.id.infregion)
        val textViewCapital = findViewById<TextView>(R.id.Capitale)
        val textViewSubregion = findViewById<TextView>(R.id.infregion)
        val textViewIndependent = findViewById<TextView>(R.id.infidpd)
        val textViewUnMember = findViewById<TextView>(R.id.infmemberun)
        val textViewCurrency = findViewById<TextView>(R.id.infmonnaie)
        val textViewLanguage = findViewById<TextView>(R.id.inflangue)
        val textViewDemonym = findViewById<TextView>(R.id.infdeno)
        val textViewPopulation = findViewById<TextView>(R.id.infpop)
        val textViewDrivesOn = findViewById<TextView>(R.id.infconduite)

        textViewName.text = countryName
        textViewDescription.text = countryDescription
        textViewRegion.text = countryRegion
        textViewCapital.text = countryCapital
        textViewSubregion.text = countrySubregion
        textViewIndependent.text = if (countryIndependent) "OUI" else "NON"
        textViewUnMember.text = if (countryUnMember) "OUI" else "NON"
        textViewCurrency.text = countryCurrency
        textViewLanguage.text = countryLanguage
        textViewDemonym.text = countryDemonym
        textViewPopulation.text = countryPopulation.toString()
        textViewDrivesOn.text = countryDrivesOn
    }
}