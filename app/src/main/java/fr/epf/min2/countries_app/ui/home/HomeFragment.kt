package fr.epf.min2.countries_app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.databinding.FragmentHomeBinding
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.ui.adapter.CountryPlaylistAdapter
import fr.epf.min2.countries_app.ui.adapter.PlaylistAdapter

class HomeFragment : Fragment() {
    private val TAG : String = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var africaAdapter: CountryPlaylistAdapter
    // Définissez d'autres adaptateurs pour les autres continents

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialisez vos adaptateurs
        playlistAdapter = PlaylistAdapter(mutableListOf())
        africaAdapter = CountryPlaylistAdapter(mutableListOf())
        // Initialisez d'autres adaptateurs pour les autres continents

        // Configurez vos RecyclerViews
        val playlistRecyclerView: RecyclerView = binding.recyclerPlay
        playlistRecyclerView.layoutManager = LinearLayoutManager(context)
        playlistRecyclerView.adapter = playlistAdapter

        val africaRecyclerView: RecyclerView = binding.recyclerAfrique
        africaRecyclerView.layoutManager = LinearLayoutManager(context)
        africaRecyclerView.adapter = africaAdapter
        // Configurez d'autres RecyclerViews pour les autres continents

        val sharedPrefManager = SharedPrefManager(requireContext())
        homeViewModel.triggerDefaultPlaylistUpdate(sharedPrefManager)

        homeViewModel._defaultPlaylists.observe(viewLifecycleOwner) { playlists ->
            // On recupere la liste des playlists
            Log.d(TAG, "The new playlists list gotten in HomeFragment from ViewModel: ${playlists.map { it.nom }}")
            // Mettez à jour votre adaptateur de playlists
           // playlistAdapter.updateData(playlists)
        }

        //liste des pays triés par continent
        /*
        homeViewModel.getCountriesByRegion().observe(viewLifecycleOwner) { countries ->
            // Filtrez les pays par continent et mettez à jour vos adaptateurs de pays
            val africanCountries = countries.filter { it.region == "Africa" }
           // africaAdapter.updateData(africanCountries)
            // Faites de même pour les autres continents
        }
        */

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
class HomeFragment : Fragment() {
    private val TAG : String = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        /*val root: View = binding.root

        val textView: TextView = binding.titrePlaylistAccueil
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.getCountriesByRegion()
        return root
        */
        val sharedPrefManager = SharedPrefManager(requireContext())
        homeViewModel.triggerDefaultPlaylistUpdate(sharedPrefManager)

        homeViewModel._defaultPlaylists.observe(viewLifecycleOwner) { playlists ->
            // On recupere la liste des playlists
            Log.d(TAG, "The new playlists list gotten in HomeFragment from ViewModel:")
            playlists.forEach {
                Log.d(TAG, "Playlist: ${it.nom} ; Content : ${it.pays.map { it.name.common }}")
            }
            // Afficher les playlists dans l'UI
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/