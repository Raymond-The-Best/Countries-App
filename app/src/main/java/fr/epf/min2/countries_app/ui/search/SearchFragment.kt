package fr.epf.min2.countries_app.ui.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.databinding.FragmentSearchBinding
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.toCountryString
import fr.epf.min2.countries_app.ui.adapter.CountryAdapter



class SearchFragment : Fragment() {

    private val TAG : String = "SearchFragment"
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var countriesList: List<Country>
    private var isSortedAtoZ = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        searchViewModel.countries.observe(viewLifecycleOwner) { countries ->
            countriesList = countries
            // Initialize RecyclerView
            val recyclerView = binding.listResultSearch
            recyclerView.layoutManager = LinearLayoutManager(context)

            // Initialize CountryAdapter
            val adapter = CountryAdapter(countriesList)
            recyclerView.adapter = adapter

        }
        // Trigger a search with an empty string to get all countries
        searchViewModel.lookUpInputedString("")


        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPrefManager = SharedPrefManager(requireContext())

        val spinner: Spinner = binding.filtreContinent
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.continents_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val continent = parent.getItemAtPosition(pos) as String
                // Mettez à jour votre recherche pour filtrer en fonction du continent sélectionné
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Si aucun continent n'est sélectionné, vous pouvez choisir de ne pas filtrer ou de réinitialiser le filtre
            }
        }

        binding.searchBarComponent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.d(TAG, "Query submitted: $query")
                    searchViewModel.lookUpInputedString(query)
                    sharedPrefManager.saveSearchQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes here if needed
                Log.d(TAG, "Text changed to $newText")
                searchViewModel.lookUpInputedString(newText ?: "")
                sharedPrefManager.getSearchHistory()
                return true
            }
        })

        // Gestion du bouton de tri alphabétique
        val aToZSearchButton = binding.AtoZSearch
        aToZSearchButton.setOnClickListener {
            if (isSortedAtoZ) {
                // If the list is currently sorted A to Z, sort it Z to A
                searchViewModel.sortZtoA()
                isSortedAtoZ = false
            } else {
                // If the list is currently sorted Z to A, sort it A to Z
                searchViewModel.sortAtoZ()
                isSortedAtoZ = true
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}