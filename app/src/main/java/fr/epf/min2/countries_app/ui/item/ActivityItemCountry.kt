package fr.epf.min2.countries_app.ui.item

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.epf.min2.countries_app.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class ActivityItemCountry : AppCompatActivity() {

    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_country)

        val countryName = intent.getStringExtra("COUNTRY_NAME")
        val countryDescription = intent.getStringExtra("COUNTRY_DESCRIPTION")

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

        val countryFlagUrl = intent.getStringExtra("COUNTRY_FLAG_URL")
        val countryLat = intent.getDoubleExtra("COUNTRY_LAT", 0.0)
        val countryLng = intent.getDoubleExtra("COUNTRY_LNG", 0.0)


        val textViewName = findViewById<TextView>(R.id.NomPays)
        val textViewRegion = findViewById<TextView>(R.id.Continent)
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

        val imageViewFlag = findViewById<ImageView>(R.id.DescDrapeauPays)
        Glide.with(this).load(countryFlagUrl).into(imageViewFlag)

        mapView = findViewById(R.id.DescMapPays)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(5.0)
        val startPoint = GeoPoint(countryLat, countryLng)
        mapController.setCenter(startPoint)

        val startMarker = Marker(mapView)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(startMarker)

        val returnButton: ImageButton = findViewById(R.id.returnButton)
        returnButton.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

}