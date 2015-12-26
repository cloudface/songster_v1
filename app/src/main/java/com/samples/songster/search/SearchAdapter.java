package com.samples.songster.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samples.songster.R;
import com.samples.songster.search.repository.dto.SongDto;

import java.util.List;

/**
 * Created by chrisbraunschweiler1 on 26/10/15.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SongDto> mSongs;

    public SearchAdapter(List<SongDto> songs){
        mSongs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SongDto song = mSongs.get(position);
        holder.mSongNameText.setText(song.getName());
        holder.mArtistNameText.setText(song.getArtist());
        holder.mAlbumNameText.setText(song.getAlbum());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mSongNameText;
        private final TextView mArtistNameText;
        private final TextView mAlbumNameText;

        public ViewHolder(View itemView) {
            super(itemView);
            mSongNameText = (TextView) itemView.findViewById(R.id.songNameText);
            mArtistNameText = (TextView) itemView.findViewById(R.id.artistNameText);
            mAlbumNameText = (TextView) itemView.findViewById(R.id.albumNameText);
        }
    }
}
