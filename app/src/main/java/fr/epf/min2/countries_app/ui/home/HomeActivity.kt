package fr.epf.min2.countries_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.epf.min2.countries_app.databinding.ActivityHomeBinding

class HomeActivity : Fragment() {

    private var _binding: ActivityHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = ActivityHomeBinding.inflate(inflater, container, false)
        /*val root: View = binding.root

        val textView: TextView = binding.titrePlaylistAccueil
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
        */
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}