package fr.epf.min2.countries_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import fr.epf.min2.countries_app.api.UpdateSavedData
import fr.epf.min2.countries_app.dataManipulation.LocalDataLookup
import fr.epf.min2.countries_app.databinding.ActivityMainBinding
import fr.epf.min2.countries_app.save.SavedDataLoader

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_playlists
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        loadDataFromLocal(this)
        UpdateSavedData.updateAllData(this)
    }
    private fun loadDataFromLocal(context : Context) {
        // Load the data from the json file
        SavedDataLoader.getInstance().loadCountriesFromJsonFile(context)
        Log.d(TAG, "Loaded countries")

    }
}