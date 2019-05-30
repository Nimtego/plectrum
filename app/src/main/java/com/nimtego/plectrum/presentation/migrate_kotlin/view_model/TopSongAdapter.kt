package com.nimtego.plectrum.presentation.migrate_kotlin.view_model

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.nimtego.plectrum.R
import com.nimtego.plectrum.data.entity.Album
import com.nimtego.plectrum.data.entity.Song
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class TopSongAdapter(private val models: List<Song>?, parent: Context) : RecyclerView.Adapter<TopSongAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onUserItemClicked(songModel: Song)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.song_form_dashboard, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = this.models!![position]
        holder.albumName.text = song.artistName
        holder.artistName.text = song.trackName
        holder.pb!!.visibility = View.VISIBLE
        holder.itemView.setOnClickListener {
            if (this.onItemClickListener != null) {
                this.onItemClickListener!!.onUserItemClicked(song)
            }
        }
        //todo
        Picasso.get().load(models[position].trackArtWorkUrl
                .replace("100x100", "200x200"))
                .into(holder.albumImage, object : Callback {
                    override fun onSuccess() {
                        if (holder.pb != null)
                            holder.pb!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception) {
                        if (holder.pb != null)
                            holder.pb!!.visibility = View.GONE
                    }
                })
//        holder.cv.cardElevation = 5f
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumImage: ImageView
        var albumName: TextView
        var artistName: TextView
        var pb: ProgressBar? = null
        var cv: CardView
        var card: ConstraintLayout

        init {
            albumImage = itemView.findViewById(R.id.album_image)
            albumName = itemView.findViewById(R.id.artist_name)
            artistName = itemView.findViewById(R.id.album_name)
            pb = itemView.findViewById(R.id.image_progress_bar)
            card = itemView.findViewById(R.id.card)
            cv = itemView.findViewById(R.id.cv)

        }
    }
}