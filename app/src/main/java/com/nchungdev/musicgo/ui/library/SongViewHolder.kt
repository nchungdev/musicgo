package com.nchungdev.musicgo.ui.library

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.util.Uri2Bitmap

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageCover = itemView.findViewById<ImageView>(R.id.imageCover)
    private val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
    private val textArtist = itemView.findViewById<TextView>(R.id.textArtist)

    fun bindData(song: Song, onItemClickListener: (Int) -> Unit) {
        textArtist.text = song.artist
        textTitle.text = song.title
        Uri2Bitmap(itemView.context, song.cover, R.drawable.ic_play_24dp) {
            imageCover.setImageBitmap(it)
        }
        itemView.setOnClickListener {
            onItemClickListener(adapterPosition - 1)
        }
    }
}
