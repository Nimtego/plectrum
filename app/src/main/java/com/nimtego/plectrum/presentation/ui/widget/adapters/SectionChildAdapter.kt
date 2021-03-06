package com.nimtego.plectrum.presentation.ui.widget.adapters

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
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.ChildViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class SectionChildAdapter (
        private val models: List<ChildViewModel>
) : RecyclerView.Adapter<SectionChildAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onUserItemClicked(childViewModel: ChildViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_tab_content_child_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{ _: View ->
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                this.models.get(adapterPosition).let {
                    this.onItemClickListener?.onUserItemClicked(it)
                }
            }
        }
        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sectionModel = this.models[position]
        holder.albumName.text = sectionModel.mainName()
        holder.artistName.text = sectionModel.minorName()
        holder.pb!!.visibility = View.VISIBLE
//        holder.itemView.setOnClickListener {
//            if (this.onItemClickListener != null) {
//                this.onItemClickListener!!.onUserItemClicked(sectionModel)
//            }
//        }
        //todo
        Picasso.get().load(models[position].imageUrl()
                .replace("100x100", "200x200"))
                .into(holder.albumImage, object : Callback {
                    override fun onSuccess() {
                            holder.pb?.visibility = View.GONE
                    }

                    override fun onError(e: Exception) {
                            holder.pb?.visibility = View.GONE
                    }
                })
//        holder.cv.cardElevation = 5f
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return models.size
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