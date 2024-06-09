package fr.epf.min2.countries_app.ui.item

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.adapter.CountryAdapter
import fr.epf.min2.countries_app.ui.adapter.CountryPlaylistAdapter

class ActivityItemPlaylist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_playlist)

        val playlistName = intent.getStringExtra("PlaylistName")
        val playlistCountries = intent.getParcelableArrayListExtra<Country>("PlaylistCountries")

        val playlistNameTextView = findViewById<TextView>(R.id.nomPlaylist)
        val countriesRecyclerView = findViewById<RecyclerView>(R.id.listPaysPlaylist)

        playlistNameTextView.text = playlistName

        val adapter = CountryPlaylistAdapter(playlistCountries!!)
        countriesRecyclerView.layoutManager = LinearLayoutManager(this)
        countriesRecyclerView.adapter = adapter

        val returnButton: ImageButton = findViewById(R.id.returnbuttonPlaylist)
        returnButton.setOnClickListener {
            finish()
        }
    }
}