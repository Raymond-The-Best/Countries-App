package fr.epf.min2.countries_app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import fr.epf.min2.countries_app.databinding.FragmentHomeBinding
import fr.epf.min2.countries_app.save.PlaylistManager
import fr.epf.min2.countries_app.save.SharedPrefManager

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
            Log.d(TAG, "The new playlists list gotten in HomeFragment from ViewModel: ${playlists.map { it.nom }}")
            // Afficher les playlists dans l'UI
        }
        /*PlaylistManager.getInstance(sharedPrefManager).playlists.observe(viewLifecycleOwner) {
            Log.d(TAG, "The new playlists list gotten in HomeFragment from Manager: ${it.map { it.nom }}")
        }*/


        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}