package fr.epf.min2.countries_app.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.epf.min2.countries_app.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {/*
        val playlistsViewModel =
            ViewModelProvider(this).get(PlaylistsViewModel::class.java)

        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPlaylists
        playlistsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }*/
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your RecyclerView and its adapter here
        // For example:
        // binding.recyclerView.layoutManager = LinearLayoutManager(context)
        // binding.recyclerView.adapter = MyAdapter(myDataset)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}