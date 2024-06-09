package fr.epf.min2.countries_app.ui.adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min2.countries_app.R
import fr.epf.min2.countries_app.save.model.Playlist
import fr.epf.min2.countries_app.ui.item.ActivityItemPlaylist


class PlaylistAdapter (private val playlists: List<Playlist>) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    inner class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomPlaylistVerti: TextView = view.findViewById(R.id.nomPlaylistVerti)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedPlaylist = playlists[position]
                    val intent = Intent(view.context, ActivityItemPlaylist::class.java)
                    intent.putExtra("PlaylistName", clickedPlaylist.nom)
                    intent.putParcelableArrayListExtra("PlaylistCountries", ArrayList(clickedPlaylist.pays))
                    view.context.startActivity(intent)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_verticale_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.nomPlaylistVerti.text = playlist.nom

    }

    override fun getItemCount() = playlists.size
}