package com.example.flo_download

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_download.databinding.ItemSavedBinding

class SavedRVAdapter():
    RecyclerView.Adapter<SavedRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedRVAdapter.ViewHolder {
        val binding: ItemSavedBinding = ItemSavedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChange")
    fun addSongs(songs: ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChange")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSavedBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Song){
            binding.itemSongTitleTv.text = album.title
            binding.itemSongSingerTv.text = album.singer
            binding.itemSongImgIv.setImageResource(album.coverImg!!)
        }
    }
}