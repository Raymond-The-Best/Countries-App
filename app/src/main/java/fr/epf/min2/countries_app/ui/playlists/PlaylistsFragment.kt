package fr.epf.min2.countries_app.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.epf.min2.countries_app.databinding.FragmentPlaylistsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.min2.countries_app.save.model.Country
import fr.epf.min2.countries_app.save.model.Playlist
import fr.epf.min2.countries_app.ui.adapter.PlaylistAdapter
import java.util.Date

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*

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
        val myDataset = listOf(
            Playlist("Playlist 1", Date(), listOf<Country>(), false, true),
            Playlist("Playlist 2", Date(), listOf<Country>(), false, true),
            Playlist("Playlist 3", Date(), listOf<Country>(), false, true),
            Playlist("Playlist 4", Date(), listOf<Country>(), false, true),
            Playlist("Playlist 5", Date(), listOf<Country>(), false, true)
        ) // Replace this with your actual data
        binding.affichPlaylist.layoutManager = LinearLayoutManager(context)
        binding.affichPlaylist.adapter = PlaylistAdapter(myDataset)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}