package fr.epf.min2.countries_app.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.databinding.FragmentSearchBinding
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.ui.adapter.CountryAdapter
import retrofit2.Call

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var countriesList: List<Country>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPrefManager = SharedPrefManager(requireContext())

        binding.searchBarComponent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    /*sharedPrefManager.saveSearchQuery(query)
                    searchViewModel.countries.observe(viewLifecycleOwner, { countries ->
                        // Utiliser l'objet countries pour afficher les résultats
                    })*/

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes here if needed
                return true
            }
        })

        // Generate a list of 20 Country objects
        countriesList = Country.generate(20)
        // Initialize RecyclerView
        val recyclerView = binding.listResultSearch
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize CountryAdapter
        val adapter = CountryAdapter(countriesList)
        recyclerView.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun  getAllCountries(searchViewModel : SearchViewModel) {
         /*searchViewModel.countries.observe(viewLifecycleOwner, { countries ->
             countriesList = countries
        })*/

    }
}