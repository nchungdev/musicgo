package com.nchungdev.musicgo.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.repository.Song

class LibraryAdapter(
    private val songs: List<Song>,
    private val onItemClickListener: (Song) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, @ViewType viewType: Int) =
        when (viewType) {
            ViewType.HEADER -> {
                HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            }
            ViewType.SONG -> SongViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_song,
                    parent,
                    false
                )
            )
            else -> EmptyHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty,
                    parent,
                    false
                )
            )
        }

    override fun getItemCount() = songs.size + 1

    @ViewType
    override fun getItemViewType(position: Int) =
        if (position == 0) ViewType.HEADER else ViewType.SONG

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder && position > 0) {
            holder.bindData(songs[position - 1], onItemClickListener)
        }
    }
}
