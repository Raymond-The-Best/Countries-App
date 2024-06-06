package fr.epf.min2.countries_app.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.epf.min2.countries_app.databinding.FragmentSearchBinding
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.toCountryString

class SearchFragment : Fragment() {

    private val TAG : String = "SearchFragment"
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
        //val factory = SavedDataViewModelFactory(this, savedDataLoader)
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        searchViewModel.countries.observe(viewLifecycleOwner) { countries ->
            countriesList = countries
            Log.d(TAG, "The new countries list gotten in SearchFragment: ${countries.toCountryString()}")
        }

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPrefManager = SharedPrefManager(requireContext())

        binding.searchBarComponent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.d(TAG, "Query submitted: $query")
                    searchViewModel.lookUpInputedString(query)
                    /*sharedPrefManager.saveSearchQuery(query)
                    searchViewModel.countries.observe(viewLifecycleOwner, { countries ->
                        // Utiliser l'objet countries pour afficher les résultats
                    })*/

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes here if needed
                Log.d(TAG, "Text changed to $newText")
                searchViewModel.lookUpInputedString(newText ?: "")
                return true
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}