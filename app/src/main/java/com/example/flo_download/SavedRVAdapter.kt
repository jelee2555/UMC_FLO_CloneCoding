package com.example.flo_download

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_download.databinding.ItemSavedBinding

class SavedRVAdapter(private val savedList: ArrayList<Saved>): RecyclerView.Adapter<SavedRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedRVAdapter.ViewHolder {
        val binding: ItemSavedBinding = ItemSavedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedRVAdapter.ViewHolder, position: Int) {
        holder.bind(savedList[position])
    }

    override fun getItemCount(): Int = savedList.size

    inner class ViewHolder(val binding: ItemSavedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Saved){
            binding.itemSongTitleTv.text = album.title
            binding.itemSongSingerTv.text = album.singer
            binding.itemSongImgIv.setImageResource(album.coverImg!!)
        }
    }
}