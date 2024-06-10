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
import fr.epf.min2.countries_app.save.SavedDataLoader
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.ui.adapter.CountryAdapterHori
import fr.epf.min2.countries_app.ui.adapter.CountryPlaylistAdapter
import fr.epf.min2.countries_app.ui.adapter.PlaylistAdapter
import fr.epf.min2.countries_app.ui.adapter.PlaylistAdapterHori

class HomeFragment : Fragment() {
    private val TAG : String = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var africaAdapter: CountryAdapterHori
    private lateinit var americaAdapter: CountryAdapterHori
    private lateinit var asiaAdapter: CountryAdapterHori
    private lateinit var europeAdapter: CountryAdapterHori
    private lateinit var oceaniaAdapter: CountryAdapterHori
    private lateinit var antarticAdapter: CountryAdapterHori

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        playlistAdapter = PlaylistAdapter(mutableListOf())
        africaAdapter = CountryAdapterHori(mutableListOf())
        americaAdapter = CountryAdapterHori(mutableListOf())
        asiaAdapter = CountryAdapterHori( mutableListOf())
        europeAdapter = CountryAdapterHori( mutableListOf())
        oceaniaAdapter = CountryAdapterHori( mutableListOf())
        antarticAdapter = CountryAdapterHori( mutableListOf())

        val playlistRecyclerView: RecyclerView = binding.recyclerPlay
        playlistRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        playlistRecyclerView.adapter = playlistAdapter

        val africaRecyclerView: RecyclerView = binding.recyclerAfrique
        africaRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        africaRecyclerView.adapter = africaAdapter

        val americaRecyclerView: RecyclerView = binding.recyclerAmerique
        americaRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        americaRecyclerView.adapter = americaAdapter

        val asiaRecyclerView: RecyclerView = binding.recyclerAsie
        asiaRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        asiaRecyclerView.adapter = asiaAdapter

        val europeRecyclerView: RecyclerView = binding.recyclerEurope
        europeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        europeRecyclerView.adapter = europeAdapter

        val oceaniaRecyclerView: RecyclerView = binding.recyclerOceanie
        oceaniaRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        oceaniaRecyclerView.adapter = oceaniaAdapter

        val antarticaRecyclerView: RecyclerView = binding.recyclerAntarctique
        antarticaRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        antarticaRecyclerView.adapter = antarticAdapter

        val sharedPrefManager = SharedPrefManager(requireContext())
        homeViewModel.triggerDefaultPlaylistUpdate(sharedPrefManager)

        homeViewModel._defaultPlaylists.observe(viewLifecycleOwner) { playlists ->
            Log.d(TAG, "The new playlists list gotten in HomeFragment from ViewModel: ${playlists.map { it.nom }}")
            playlistRecyclerView.adapter = PlaylistAdapterHori(playlists)
        }


        homeViewModel._countriesByRegion.observe(viewLifecycleOwner) { countries ->

            val africanCountries = countries["Africa"] ?: emptyList()
            val americanCountries = countries["Americas"] ?: emptyList()
            val asianCountries = countries["Asia"] ?: emptyList()
            val europeanCountries = countries["Europe"] ?: emptyList()
            val oceaniaCountries = countries["Oceania"] ?: emptyList()
            val antarcticaCountryNames = listOf("Terre australes et antarctiques", "Antarctique", "Île Bouvet", "Îles Heard-et-MacDonald", "Géorgie du Sud-et-les Îles Sandwich du Sud")
            val antarcticaCountries = antarcticaCountryNames.map { SavedDataLoader.getInstance().lookupByName(it) }.filterNotNull()

            africaAdapter.updateCountries(africanCountries)
            americaAdapter.updateCountries(americanCountries)
            asiaAdapter.updateCountries(asianCountries)
            europeAdapter.updateCountries(europeanCountries)
            oceaniaAdapter.updateCountries(oceaniaCountries)
            antarticAdapter.updateCountries(antarcticaCountries)

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
