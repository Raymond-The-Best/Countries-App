package fr.epf.min2.countries_app.ui.playlists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.epf.min2.countries_app.databinding.FragmentPlaylistsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.min2.countries_app.save.SharedPrefManager
import fr.epf.min2.countries_app.ui.adapter.PlaylistAdapter

private const val TAG = "PlaylistsFragment"
class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val playlistsViewModel =
            ViewModelProvider(this).get(PlaylistsViewModel::class.java)

        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPlaylists
        playlistsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlistsViewModel = ViewModelProvider(this).get(PlaylistsViewModel::class.java)
        val sharedPrefManager = SharedPrefManager(requireContext())
        playlistsViewModel.triggerPlaylistsUpdate(sharedPrefManager)

        playlistsViewModel._playlists.observe(viewLifecycleOwner) { playlists ->
            // On recupere la liste des playlists
            Log.d(TAG, "The new playlists list gotten in PlaylistsFragment: ${playlists.map { it.nom }}")
            playlists.forEach {
                Log.d(TAG, "Playlist: ${it.nom} ; Content : ${it.pays.map { it.name.common }}")
            }
            binding.affichPlaylist.layoutManager = LinearLayoutManager(context)
            binding.affichPlaylist.adapter = PlaylistAdapter(playlists.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}