package com.nimtego.itunes.presentation.main.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nimtego.itunes.R;
import com.nimtego.itunes.presentation.main.model.ArtistModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private List<ArtistModel> models;

    public ArtistAdapter(List<ArtistModel> model, Context parent) {
        this.models = model;
    }

    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_form, parent, false);
        return new ArtistAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ArtistAdapter.ViewHolder holder, final int position) {
        holder.albumName.setText(String.valueOf(models.get(position).getPrimaryGenreName()));
        holder.artistName.setText(models.get(position).getArtistName());
        holder.pb.setVisibility(View.VISIBLE);
        Picasso.get().load(models.get(position).getArtistViewUrl())
                .into(holder.albumImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.pb != null)
                            holder.pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        if (holder.pb != null)
                            holder.pb.setVisibility(View.GONE);
                    }
                });
        holder.cv.setCardElevation(5);
    }

    @Override
    public int getItemCount() {
        if (models == null)
            return 0;
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName;
        TextView artistName;
        ProgressBar pb;
        CardView cv;
        ConstraintLayout card;

        public ViewHolder(View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.album_image);
            albumName = itemView.findViewById(R.id.artist_name);
            artistName = itemView.findViewById(R.id.album_name);
            pb = itemView.findViewById(R.id.image_progress_bar);
            card = itemView.findViewById(R.id.card);
            cv = itemView.findViewById(R.id.cv);

        }
    }
}
