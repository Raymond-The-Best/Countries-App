package fr.epf.min2.countries_app.ui.playlists

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.epf.min2.countries_app.databinding.FragmentPlaylistsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.PlaylistManager
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

        val ajouterNvllePlaylist: ImageButton = view.findViewById(R.id.ajouterNvllePlaylist)

        ajouterNvllePlaylist.setOnClickListener {
            // Create an EditText
            val editText = EditText(context).apply {
                inputType = InputType.TYPE_CLASS_TEXT
                hint = "Nom playlist"
            }
            val playlistManager : PlaylistManager = PlaylistManager.getInstance(sharedPrefManager)
            // Create an AlertDialog
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Nouvelle Playlist")
                .setMessage("Entez le nom de la playlist")
                .setView(editText)
                .setPositiveButton("Add") { _, _ ->
                    val playlistName = editText.text.toString()
                    // Use the playlistName here to create a new playlist
                    playlistManager.createPlaylist(playlistName)
                    // Update the RecyclerView
                    playlistsViewModel.triggerPlaylistsUpdate(sharedPrefManager)
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}