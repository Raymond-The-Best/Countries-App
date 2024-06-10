package fr.epf.min2.countries_app.ui.item

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.adapter.CountryAdapter
import fr.epf.min2.countries_app.ui.adapter.CountryPlaylistAdapter
private const val TAG = "ActivityItemPlaylist"
class ActivityItemPlaylist : AppCompatActivity(), CountryPlaylistAdapter.OnDeleteButtonClickListener, CountryPlaylistAdapter.onFavoriteButtonClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_playlist)

        val playlistName = intent.getStringExtra("PlaylistName")
        val playlistCountries = intent.getParcelableArrayListExtra<Country>("PlaylistCountries")

        val playlistNameTextView = findViewById<TextView>(R.id.nomPlaylist)
        val countriesRecyclerView = findViewById<RecyclerView>(R.id.listPaysPlaylist)

        playlistNameTextView.text = playlistName

        val adapter = playlistName?.let { CountryPlaylistAdapter(it, playlistCountries!!, this, this) }
        countriesRecyclerView.layoutManager = LinearLayoutManager(this)
        countriesRecyclerView.adapter = adapter

        val returnButton: ImageButton = findViewById(R.id.returnbuttonPlaylist)
        returnButton.setOnClickListener {
            finish()
        }

    }
    override fun onDeleteButtonClick() {
        val playlistName = intent.getStringExtra("PlaylistName")
        val playlistCountries = intent.getParcelableArrayListExtra<Country>("PlaylistCountries")
        val adapter = playlistName?.let { CountryPlaylistAdapter(it, playlistCountries!!, this, this) }
    }
    override fun onFavoriteButtonClick() {
        Log.d(TAG, "Favorite button clicked")
        onDeleteButtonClick()
    }
}